package com.starnft.star.framework.solidity;//package com.ruoyi.framework.solidity;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import cn.hutool.core.io.resource.ResourceUtil;
//import cn.hutool.core.lang.func.Func1;
//import io.reactivex.Observable;
//import org.web3j.abi.EventEncoder;
//import org.web3j.abi.FunctionEncoder;
//import org.web3j.abi.TypeReference;
//import org.web3j.abi.datatypes.Address;
//import org.web3j.abi.datatypes.Bool;
//import org.web3j.abi.datatypes.DynamicArray;
//import org.web3j.abi.datatypes.Event;
//import org.web3j.abi.datatypes.Function;
//import org.web3j.abi.datatypes.Type;
//import org.web3j.abi.datatypes.Utf8String;
//import org.web3j.abi.datatypes.generated.Uint256;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.DefaultBlockParameter;
//import org.web3j.protocol.core.RemoteCall;
//import org.web3j.protocol.core.methods.request.EthFilter;
//import org.web3j.protocol.core.methods.response.Log;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.tx.Contract;
//import org.web3j.tx.TransactionManager;
//
///**
// * <p>Auto generated code.
// * <p><strong>Do not modify!</strong>
// * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
// * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
// * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
// *
// * <p>Generated with web3j version 3.4.0.
// */
//public class SimpleNFT1155BK extends Contract {
//
////    private static StringBuffer stringBuffer=new StringBuffer();
//
////    private static final String BINARY = "";
//
//    public static final String FUNC_BALANCEOF = "balanceOf";
//
//    public static final String FUNC_BALANCEOFBATCH = "balanceOfBatch";
//
//    public static final String FUNC_BURN = "burn";
//
//    public static final String FUNC_BURNBATCH = "burnBatch";
//
//    public static final String FUNC_BURNFORMINT = "burnForMint";
//
//    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";
//
//    public static final String FUNC_MINT = "mint";
//
//    public static final String FUNC_MINTBATCH = "mintBatch";
//
//    public static final String FUNC_NAME = "name";
//
//    public static final String FUNC_OWNER = "owner";
//
//    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";
//
//    public static final String FUNC_SAFEBATCHTRANSFERFROM = "safeBatchTransferFrom";
//
//    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";
//
//    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";
//
//    public static final String FUNC_SETURI = "setURI";
//
//    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";
//
//    public static final String FUNC_SYMBOL = "symbol";
//
//    public static final String FUNC_TOKENURI = "tokenURI";
//
//    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";
//
//    public static final String FUNC_URI = "uri";
//
//    public static final String FUNC_URIS = "uris";
//
//    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
//            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
//            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
//    ;
//
//    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
//            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
//            Arrays.<TypeReference<?>>asList());
//    ;
//
//    public static final Event TRANSFERBATCH_EVENT = new Event("TransferBatch",
//            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
//            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
//    ;
//
//    public static final Event TRANSFERSINGLE_EVENT = new Event("TransferSingle",
//            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
//            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
//    ;
//
//    public static final Event URI_EVENT = new Event("URI",
//            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
//            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
//    ;
//
//    protected SimpleNFT1155BK(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
//
//        super(ResourceUtil.readUtf8Str("bins/SimpleNFT1155.bin"), contractAddress, web3j, credentials, gasPrice, gasLimit);
//    }
//
//    protected SimpleNFT1155BK(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
//        super(ResourceUtil.readUtf8Str("bins/SimpleNFT1155.bin"), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
//    }
//
//    public static RemoteCall<SimpleNFT1155BK> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, String _uri) {
//        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
//                new org.web3j.abi.datatypes.Utf8String(_symbol),
//                new org.web3j.abi.datatypes.Utf8String(_uri)));
//        return deployRemoteCall(SimpleNFT1155BK.class, web3j, credentials, gasPrice, gasLimit, ResourceUtil.readUtf8Str("bins/SimpleNFT1155.bin"), encodedConstructor);
//    }
//
//    public static RemoteCall<SimpleNFT1155BK> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, String _uri) {
//        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name),
//                new org.web3j.abi.datatypes.Utf8String(_symbol),
//                new org.web3j.abi.datatypes.Utf8String(_uri)));
//        return deployRemoteCall(SimpleNFT1155BK.class, web3j, transactionManager, gasPrice, gasLimit, ResourceUtil.readUtf8Str("bins/SimpleNFT1155.bin"), encodedConstructor);
//    }
//
//    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
//        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }
//
//    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(EthFilter filter) {
//        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalForAllEventResponse>() {
//            @Override
//            public ApprovalForAllEventResponse call(Log log) {
//                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
//                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
//                typedResponse.log = log;
//                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
//                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
//                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
//                return typedResponse;
//            }
//        });
//    }
//
//    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
//        return approvalForAllEventObservable(filter);
//    }
//
//    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
//        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }
//
//    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(EthFilter filter) {
//        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
//            @Override
//            public OwnershipTransferredEventResponse call(Log log) {
//                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
//                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
//                typedResponse.log = log;
//                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
//                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
//                return typedResponse;
//            }
//        });
//    }
//
//    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
//        return ownershipTransferredEventObservable(filter);
//    }
//
//    public List<TransferBatchEventResponse> getTransferBatchEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERBATCH_EVENT, transactionReceipt);
//        ArrayList<TransferBatchEventResponse> responses = new ArrayList<TransferBatchEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
//            typedResponse.ids = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.values = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }
//
//    public Observable<TransferBatchEventResponse> transferBatchEventObservable(EthFilter filter) {
//        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferBatchEventResponse>() {
//            @Override
//            public TransferBatchEventResponse call(Log log) {
//                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERBATCH_EVENT, log);
//                TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
//                typedResponse.log = log;
//                typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
//                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
//                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
//                typedResponse.ids = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
//                typedResponse.values = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
//                return typedResponse;
//            }
//        });
//    }
//
//    public Observable<TransferBatchEventResponse> transferBatchEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(TRANSFERBATCH_EVENT));
//        return transferBatchEventObservable(filter);
//    }
//
//    public List<TransferSingleEventResponse> getTransferSingleEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERSINGLE_EVENT, transactionReceipt);
//        ArrayList<TransferSingleEventResponse> responses = new ArrayList<TransferSingleEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
//            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }
//
//    public Observable<TransferSingleEventResponse> transferSingleEventObservable(EthFilter filter) {
//        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferSingleEventResponse>() {
//            @Override
//            public TransferSingleEventResponse call(Log log) {
//                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERSINGLE_EVENT, log);
//                TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
//                typedResponse.log = log;
//                typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
//                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
//                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
//                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
//                return typedResponse;
//            }
//        });
//    }
//
//    public Observable<TransferSingleEventResponse> transferSingleEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(TRANSFERSINGLE_EVENT));
//        return transferSingleEventObservable(filter);
//    }
//
//    public List<URIEventResponse> getURIEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(URI_EVENT, transactionReceipt);
//        ArrayList<URIEventResponse> responses = new ArrayList<URIEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            URIEventResponse typedResponse = new URIEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }
//
//    public Observable<URIEventResponse> uRIEventObservable(EthFilter filter) {
//        return web3j.ethLogObservable(filter).map(new Func1<Log, URIEventResponse>() {
//            @Override
//            public URIEventResponse call(Log log) {
//                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(URI_EVENT, log);
//                URIEventResponse typedResponse = new URIEventResponse();
//                typedResponse.log = log;
//                typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//                typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
//                return typedResponse;
//            }
//        });
//    }
//
//    public Observable<URIEventResponse> uRIEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
//        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
//        filter.addSingleTopic(EventEncoder.encode(URI_EVENT));
//        return uRIEventObservable(filter);
//    }
//
//    public RemoteCall<TransactionReceipt> balanceOf(String account, BigInteger id) {
//        final Function function = new Function(
//                FUNC_BALANCEOF,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account),
//                new org.web3j.abi.datatypes.generated.Uint256(id)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> balanceOfBatch(List<String> accounts, List<BigInteger> ids) {
//        final Function function = new Function(
//                FUNC_BALANCEOFBATCH,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
//                        org.web3j.abi.Utils.typeMap(accounts, org.web3j.abi.datatypes.Address.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class))),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> burn(BigInteger _id, BigInteger _amount) {
//        final Function function = new Function(
//                FUNC_BURN,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id),
//                new org.web3j.abi.datatypes.generated.Uint256(_amount)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> burnBatch(List<BigInteger> _ids, List<BigInteger> _amounts) {
//        final Function function = new Function(
//                FUNC_BURNBATCH,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_ids, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class))),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> burnForMint(String _from, List<BigInteger> _burnIds, List<BigInteger> _burnAmounts, List<BigInteger> _mintIds, List<BigInteger> _mintAmounts) {
//        final Function function = new Function(
//                FUNC_BURNFORMINT,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_burnIds, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_burnAmounts, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_mintIds, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_mintAmounts, org.web3j.abi.datatypes.generated.Uint256.class))),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> isApprovedForAll(String account, String operator) {
//        final Function function = new Function(
//                FUNC_ISAPPROVEDFORALL,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account),
//                new org.web3j.abi.datatypes.Address(operator)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> mint(String _to, BigInteger _id, BigInteger _amount) {
//        final Function function = new Function(
//                FUNC_MINT,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to),
//                new org.web3j.abi.datatypes.generated.Uint256(_id),
//                new org.web3j.abi.datatypes.generated.Uint256(_amount)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> mintBatch(String _to, List<BigInteger> _ids, List<BigInteger> _amounts, String _data) {
//        final Function function = new Function(
//                FUNC_MINTBATCH,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_ids, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.Utf8String(_data)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> name() {
//        final Function function = new Function(
//                FUNC_NAME,
//                Arrays.<Type>asList(),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> owner() {
//        final Function function = new Function(
//                FUNC_OWNER,
//                Arrays.<Type>asList(),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> renounceOwnership() {
//        final Function function = new Function(
//                FUNC_RENOUNCEOWNERSHIP,
//                Arrays.<Type>asList(),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> safeBatchTransferFrom(String from, String to, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) {
//        final Function function = new Function(
//                FUNC_SAFEBATCHTRANSFERFROM,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from),
//                new org.web3j.abi.datatypes.Address(to),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
//                        org.web3j.abi.Utils.typeMap(amounts, org.web3j.abi.datatypes.generated.Uint256.class)),
//                new org.web3j.abi.datatypes.DynamicBytes(data)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger id, BigInteger amount, byte[] data) {
//        final Function function = new Function(
//                FUNC_SAFETRANSFERFROM,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from),
//                new org.web3j.abi.datatypes.Address(to),
//                new org.web3j.abi.datatypes.generated.Uint256(id),
//                new org.web3j.abi.datatypes.generated.Uint256(amount),
//                new org.web3j.abi.datatypes.DynamicBytes(data)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
//        final Function function = new Function(
//                FUNC_SETAPPROVALFORALL,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(operator),
//                new org.web3j.abi.datatypes.Bool(approved)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> setURI(BigInteger _id, String _uri) {
//        final Function function = new Function(
//                FUNC_SETURI,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id),
//                new org.web3j.abi.datatypes.Utf8String(_uri)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> supportsInterface(byte[] interfaceId) {
//        final Function function = new Function(
//                FUNC_SUPPORTSINTERFACE,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> symbol() {
//        final Function function = new Function(
//                FUNC_SYMBOL,
//                Arrays.<Type>asList(),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> tokenURI(BigInteger param0) {
//        final Function function = new Function(
//                FUNC_TOKENURI,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
//        final Function function = new Function(
//                FUNC_TRANSFEROWNERSHIP,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> uri(BigInteger param0) {
//        final Function function = new Function(
//                FUNC_URI,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> uri() {
//        final Function function = new Function(
//                FUNC_URI,
//                Arrays.<Type>asList(),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public RemoteCall<TransactionReceipt> uris(BigInteger _id) {
//        final Function function = new Function(
//                FUNC_URIS,
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)),
//                Collections.<TypeReference<?>>emptyList());
//        return executeRemoteCallTransaction(function);
//    }
//
//    public static SimpleNFT1155BK load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
//        return new SimpleNFT1155BK(contractAddress, web3j, credentials, gasPrice, gasLimit);
//    }
//
//    public static SimpleNFT1155BK load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
//        return new SimpleNFT1155BK(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
//    }
//
//    public static class ApprovalForAllEventResponse {
//        public Log log;
//
//        public String account;
//
//        public String operator;
//
//        public Boolean approved;
//    }
//
//    public static class OwnershipTransferredEventResponse {
//        public Log log;
//
//        public String previousOwner;
//
//        public String newOwner;
//    }
//
//    public static class TransferBatchEventResponse {
//        public Log log;
//
//        public String operator;
//
//        public String from;
//
//        public String to;
//
//        public List<BigInteger> ids;
//
//        public List<BigInteger> values;
//    }
//
//    public static class TransferSingleEventResponse {
//        public Log log;
//
//        public String operator;
//
//        public String from;
//
//        public String to;
//
//        public BigInteger id;
//
//        public BigInteger value;
//    }
//
//    public static class URIEventResponse {
//        public Log log;
//
//        public BigInteger id;
//
//        public String value;
//    }
//}
