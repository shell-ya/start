package com.starnft.star.common.utils;

import io.github.novacrypto.base58.Base58;
import io.github.novacrypto.bip32.ExtendedPrivateKey;
import io.github.novacrypto.bip32.networks.Bitcoin;
import io.github.novacrypto.bip44.AddressIndex;
import io.github.novacrypto.bip44.BIP44;
import io.github.novacrypto.hashing.Sha256;
import io.github.novacrypto.toruntime.CheckedExceptionToRuntime;
import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.github.novacrypto.toruntime.CheckedExceptionToRuntime.toRuntime;

@Component
public class WalletAddrGenerator {

    @Value("${wallet.file.path: /tmp/wallet}")
    private String filePath;
    //    private String filePath = "generator/english";
    private static final String[] dict =
            {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
                    "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    private static String password = "password";

   public static void main(String[] args) {
       WalletAddrGenerator generator = new WalletAddrGenerator();
       try {
           String generate = generator.generate();
           System.out.println(generate);
       } catch (CipherException | InvalidKeySpecException | NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
       }
   }

    public String generate() throws CipherException, InvalidKeySpecException, NoSuchAlgorithmException {
        String entropy = createEntropy();
        String[] wordlist = new String[2048];
        String mnemonic = generateMnemonic(entropy, wordlist);
        List<String> params = generateKeyPairs(mnemonic);
        return "0x" + params.get(2);
    }

    public String createEntropy() {
        UUID uuid = UUID.randomUUID();
        String[] digits = uuid.toString().split("\\-");
        StringBuilder randomDigits = new StringBuilder();
        for (String digit : digits) {
            randomDigits.append(digit);
        }
        return randomDigits.toString();
    }

    public String generateMnemonic(String entropy, String[] wordlist) {
        System.out.println(entropy);

        //generate sha-256 from entropy
        String encodeStr = "";
        byte[] hash = Sha256.sha256(hexStringToByteArray(entropy));
        encodeStr = String.valueOf(Hex.encodeHex(hash));
        System.out.println(encodeStr);
        char firstSHA = encodeStr.charAt(0);
        String new_entropy = entropy + firstSHA;
        String bin_entropy = "";
        for (int i = 0; i < new_entropy.length(); i++) {
            bin_entropy += dict[Integer.parseInt(new_entropy.substring(i, i + 1), 16)];
        }
        String[] segments = new String[12];
        //hardcode
        for (int i = 0; i <= 11; i++) {
            segments[i] = bin_entropy.substring(i * 11, (i + 1) * 11);
        }

        //请修改文件的绝对路径
        String path = filePath;
        readTextFile(path, wordlist);
        String mnemonic = "";

        //generate mnemonic
        mnemonic += wordlist[Integer.valueOf(segments[0], 2)];
        for (int j = 1; j < segments.length; j++) {
            mnemonic += " " + (wordlist[Integer.valueOf(segments[j], 2)]);
        }
        return mnemonic;
    }

    public void readTextFile(String filePath, String[] wordlist) {
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int index = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    wordlist[index++] = lineTxt;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static List<String> generateKeyPairs(String mnemonic) throws InvalidKeySpecException, NoSuchAlgorithmException, CipherException {

        // 1. we just need eth wallet for now
        AddressIndex ethAddressIndex = BIP44.m().purpose44().coinType(60).account(0).external().address(0);
        AddressIndex btcAddressIndex = BIP44.m().purpose44().coinType(0).account(0).external().address(0);
        // 2. calculate seed from mnemonics , then get master/root key ; Note that the bip39 passphrase we set "" for common
        String seed;
        String salt = RandomUtil.randomString(18);
        seed = getSeed(mnemonic, salt);
        System.out.println(seed);


        ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(hexStringToByteArray(seed), Bitcoin.MAIN_NET);
        // 3. get child private key deriving from master/root key
        ExtendedPrivateKey childPrivateKey = rootKey.derive(ethAddressIndex, AddressIndex.DERIVATION);

        // 4. get key pair
        byte[] privateKeyBytes = childPrivateKey.getKey(); //child private key
        ECKeyPair keyPair = ECKeyPair.create(privateKeyBytes);
        WalletFile walletFile = Wallet.createLight(password, keyPair);//TODO: walletFile & password
        List<String> returnList = EthAddress(childPrivateKey, keyPair);

        childPrivateKey = rootKey.derive(btcAddressIndex, AddressIndex.DERIVATION);
        bitcoinAddress(childPrivateKey);

        return returnList;

    }

    public static String getSeed(String mnemonic, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        char[] chars = Normalizer.normalize(mnemonic, Normalizer.Form.NFKD).toCharArray();
        byte[] salt_ = getUtf8Bytes(salt);
        KeySpec spec = new PBEKeySpec(chars, salt_, 2048, 512);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        return String.valueOf(Hex.encodeHex(f.generateSecret(spec).getEncoded()));
    }

    private static byte[] getUtf8Bytes(final String str) {
        return toRuntime(new CheckedExceptionToRuntime.Func<byte[]>() {
            @Override
            public byte[] run() throws Exception {
                return str.getBytes("UTF-8");
            }
        });
    }


    /**
     * generate ETH privatekey, publickey and address.
     *
     * @param childPrivateKey
     * @param keyPair
     */
    private static List<String> EthAddress(ExtendedPrivateKey childPrivateKey, ECKeyPair keyPair) {
        String privateKey = childPrivateKey.getPrivateKey();
        String publicKey = childPrivateKey.neuter().getPublicKey();
        String address = Keys.getAddress(keyPair);

        System.out.println("ETH privateKey:" + privateKey);
        System.out.println("ETH publicKey:" + publicKey);
        System.out.println("ETH address:" + address);

        List<String> returnList = new ArrayList<>();
        returnList.add(privateKey);
        returnList.add(publicKey);
        returnList.add(address);
        return returnList;
    }


    /**
     * generate bitcoin privatekey, publickey and address.
     *
     * @param childPrivateKey
     */
    private static void bitcoinAddress(ExtendedPrivateKey childPrivateKey) {
        // 获取比特币私钥
        String privateKey = childPrivateKey.getPrivateKey();
        // 加80前缀和01后缀
        String rk = "80" + privateKey + "01";
        // 生成校验和
        byte[] checksum = Sha256.sha256(hexStringToByteArray(rk));
        checksum = Sha256.sha256(checksum);
        // 取校验和前4位（32bits）
        String end = String.valueOf(Hex.encodeHex(checksum)).substring(0, 8);
        rk = rk + end;
        // 进行base58编码
        String privateK = Base58.base58Encode(hexStringToByteArray(rk));


        // 获取比特币公钥
        String publicKey = childPrivateKey.neuter().getPublicKey();
        // 对公钥进行一次sha256
        byte[] pk256 = hexStringToByteArray(publicKey);
        pk256 = Sha256.sha256(pk256);
        // 进行ripe160加密（20位）
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(pk256, 0, pk256.length);
        byte[] ripemd160Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(ripemd160Bytes, 0);
        // 加00前缀（比特币主网）变成21位
        byte[] extendedRipemd160Bytes = hexStringToByteArray("00" + String.valueOf(Hex.encodeHex(ripemd160Bytes)));
        // 计算校验和
        checksum = Sha256.sha256(extendedRipemd160Bytes);
        checksum = Sha256.sha256(checksum);
        // 加校验和前4位，变成25位
        String pk = String.valueOf(Hex.encodeHex(extendedRipemd160Bytes)) + String.valueOf(Hex.encodeHex(checksum)).substring(0, 8);
        // base58加密
        String address = Base58.base58Encode(hexStringToByteArray(pk));

        System.out.println("bitcoin privateKey:" + privateK);
        System.out.println("bitcoin publicKey:" + publicKey);
        System.out.println("bitcoin address:" + address);

        generateSegwitAddress(address);
    }

    private static void generateSegwitAddress(String address) {
        byte[] decoded = Utils.parseAsHexOrBase58(address);
        // We should throw off header byte that is 0 for Bitcoin (Main)
        byte[] pureBytes = new byte[20];
        System.arraycopy(decoded, 1, pureBytes, 0, 20);
        // Than we should prepend the following bytes:
        byte[] scriptSig = new byte[pureBytes.length + 2];
        scriptSig[0] = 0x00;
        scriptSig[1] = 0x14;
        System.arraycopy(pureBytes, 0, scriptSig, 2, pureBytes.length);
        byte[] addressBytes = org.bitcoinj.core.Utils.sha256hash160(scriptSig);
        // Here are the address bytes
        byte[] readyForAddress = new byte[addressBytes.length + 1 + 4];
        // prepending p2sh header:
        readyForAddress[0] = (byte) 5;
        System.arraycopy(addressBytes, 0, readyForAddress, 1, addressBytes.length);
        // But we should also append check sum:
        byte[] checkSum = Sha256Hash.hashTwice(readyForAddress, 0, addressBytes.length + 1);
        System.arraycopy(checkSum, 0, readyForAddress, addressBytes.length + 1, 4);
        // To get the final address:
        String segwitAddress = Base58.base58Encode(readyForAddress);
        System.out.println("segwit address:" + segwitAddress);
    }

}
