package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.business.service.impl.AirdropThemeRecordServiceImpl;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business/airdrop")
public class AirdropController extends BaseController {
    @Resource
 private IStarNftThemeNumberService iStarNftThemeNumberService;

    @Resource
    private IStarNftThemeInfoService iStarNftThemeInfoService;

    @Resource
    private IAirdropThemeRecordService airdropThemeRecordService;
    /**
     * 查询编号
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listNumber')")
    @PostMapping(value = "/listNumber")
    public TableDataInfo listNumber(@RequestBody  StarNftThemeNumber nftThemeNumber)
    {
        startPage();
        List<StarNftThemeNumber> starNftThemeNumbers = iStarNftThemeNumberService.selectStarNftThemeNumberList(nftThemeNumber);
        return getDataTable(starNftThemeNumbers);
    }
    /**
     * 查询所有主题
     */
    @PreAuthorize("@ss.hasPermi('business:airdrop:listTheme')")
    @PostMapping(value = "/listTheme")
    public TableDataInfo listTheme(@RequestBody StarNftThemeInfo starNftThemeInfo)
    {
        startPage();
        List<StarNftThemeInfo> starNftThemeInfos = iStarNftThemeInfoService.selectStarNftThemeInfoList(starNftThemeInfo);
        return getDataTable(starNftThemeInfos);
    }

//    @PreAuthorize("@ss.hasPermi('business:airdrop:add')")
    @PostMapping(value = "/addAirdrop")
    public AjaxResult addAirdrop(@RequestBody AirdropThemeRecord record)
    {
        return AjaxResult.success(airdropThemeRecordService.addUserAirdrop(record));
    }

    @PostMapping(value = "/airdropList")
    public AjaxResult listTheme(@RequestBody List<AirdropRecordDto> records)
    {
        return AjaxResult.success(airdropThemeRecordService.addUserAirdropList(records));
    }

// [
//    {
//        "userId": 915512099,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522670515212288,
//                    1000522670592835584,
//                    1000522670673874944,
//                    1000522670759198720,
//                    1000522670839369728
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 294592515,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522670918299648,
//                    1000522670990708736,
//                    1000522671061966848,
//                    1000522671129272320,
//                    1000522671199838208
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 336673887,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522672134455296,
//                    1000522672222879744,
//                    1000522672295419904,
//                    1000522672377671680,
//                    1000522672457478144
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 281850262,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522672524779520,
//                    1000522672613548032,
//                    1000522672685064192,
//                    1000522672750231552,
//                    1000522672828669952
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 177704908,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522672898084864,
//                    1000522672980447232,
//                    1000522673059307520,
//                    1000522673130323968,
//                    1000522673197121536
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 747556896,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522673368596480,
//                    1000522673449652224,
//                    1000522673604939776,
//                    1000522673699520512,
//                    1000522673817157632
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 551997115,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522673908166656,
//                    1000522673985495040,
//                    1000522674059452416,
//                    1000522674141544448,
//                    1000522674206789632
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 654232977,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522674286923776,
//                    1000522674364276736,
//                    1000522674433675264,
//                    1000522674513756160,
//                    1000522674581872640
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 696700174,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522674656747520,
//                    1000522674726457344,
//                    1000522674810875904,
//                    1000522674894368768,
//                    1000522674965078016
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 189879367,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522675051880448,
//                    1000522675132198912,
//                    1000522675204362240,
//                    1000522675271286784,
//                    1000522675347787776
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 166745224,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522675415601152,
//                    1000522675489505280,
//                    1000522675572662272,
//                    1000522675646447616,
//                    1000522675744436224
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 884363953,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522675809628160,
//                    1000522675879206912,
//                    1000522675968864256,
//                    1000522676041035776,
//                    1000522676113334272
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 673887355,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522676178771968,
//                    1000522676247920640,
//                    1000522676330876928,
//                    1000522676397121536,
//                    1000522676617023488
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 574112667,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522676692123648,
//                    1000522676760276992,
//                    1000522676839985152,
//                    1000522676923379712,
//                    1000522676991016960
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 202388374,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522677067059200,
//                    1000522677138456576,
//                    1000522677213958144,
//                    1000522677292421120,
//                    1000522677369413632
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 581261597,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522677446213632,
//                    1000522677581877248,
//                    1000522677662851072,
//                    1000522677740199936,
//                    1000522677817237504
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 346397942,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522677933445120,
//                    1000522678014943232,
//                    1000522678118223872,
//                    1000522678212583424,
//                    1000522678294257664
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 309503360,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522678359674880,
//                    1000522678427795456,
//                    1000522678497374208,
//                    1000522678564818944,
//                    1000522678631538688
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 365883122,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522678705455104,
//                    1000522678775676928,
//                    1000522678848172032,
//                    1000522678916755456,
//                    1000522678991499264
//                ]
//        }
//        ]
//    }
//]
//
//        [
//    {
//        "userId": 145940619,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522679073042432,
//                    1000522679142797312,
//                    1000522679229538304,
//                    1000522679300694016,
//                    1000522679379267584
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 660361310,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522679464042496,
//                    1000522679542771712,
//                    1000522679711027200,
//                    1000522679812550656,
//                    1000522679889182720
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 146224314,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522679961108480,
//                    1000522680044523520,
//                    1000522680121090048,
//                    1000522680195358720,
//                    1000522680271978496
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 452951043,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522680352358400,
//                    1000522680434520064,
//                    1000522680524201984,
//                    1000522680608260096,
//                    1000522680706064384
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 836508943,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522680782647296,
//                    1000522680885465088,
//                    1000522680980004864,
//                    1000522681050501120,
//                    1000522681118740480
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 145428489,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522681198034944,
//                    1000522681276211200,
//                    1000522681357488128,
//                    1000522681428307968,
//                    1000522681514881024
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 450894037,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522681590640640,
//                    1000522681675792384,
//                    1000522681761878016,
//                    1000522681833639936,
//                    1000522681914363904
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 187846516,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522681995280384,
//                    1000522682067734528,
//                    1000522682139979776,
//                    1000522682213457920,
//                    1000522682311569408
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 636422054,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522682384957440,
//                    1000522682461229056,
//                    1000522682530652160,
//                    1000522682618806272,
//                    1000522682709037056
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 767781767,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522682784788480,
//                    1000522682963668992,
//                    1000522683046330368,
//                    1000522683120431104,
//                    1000522683193798656
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 546998827,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522683268395008,
//                    1000522683354677248,
//                    1000522683451056128,
//                    1000522683531452416,
//                    1000522683612504064
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 499793312,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522683686748160,
//                    1000522683752034304,
//                    1000522683829800960,
//                    1000522683904847872,
//                    1000522683979214848
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 100680580,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522684086718464,
//                    1000522684159864832,
//                    1000522684232740864,
//                    1000522684300165120,
//                    1000522684368101376
//                ]
//        }
//        ]
//    }
// ]
//
//
//         [
//    {
//        "userId": 229978589,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522684464267264,
//                    1000522684538658816,
//                    1000522684612907008,
//                    1000522684685836288,
//                    1000522684765470720
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 315326229,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522684851240960,
//                    1000522684928679936,
//                    1000522685015277568,
//                    1000522685088641024,
//                    1000522685155168256
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 274661434,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522685236006912,
//                    1000522685365596160,
//                    1000522685467488256,
//                    1000522685554896896,
//                    1000522685629161472
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 399257314,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522685704089600,
//                    1000522685781671936,
//                    1000522685859917824,
//                    1000522685946589184,
//                    1000522686021832704
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 515281046,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522686179729408,
//                    1000522686264868864,
//                    1000522686346661888,
//                    1000522686422552576,
//                    1000522686505172992
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 676214902,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522686585487360,
//                    1000522686669357056,
//                    1000522686745354240,
//                    1000522686817316864,
//                    1000522686890807296
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 569136892,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522686970118144,
//                    1000522687050436608,
//                    1000522687120908288,
//                    1000522687213129728,
//                    1000522687292006400
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 219048153,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522687363432448,
//                    1000522687452839936,
//                    1000522687529373696,
//                    1000522687616675840,
//                    1000522687717879808
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 156099501,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522687809044480,
//                    1000522687906897920,
//                    1000522688036864000,
//                    1000522688163536896,
//                    1000522688263233536
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 593190484,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522688344932352,
//                    1000522688428744704,
//                    1000522688540196864,
//                    1000522688615256064,
//                    1000522688689037312
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 130286401,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522688775114752,
//                    1000522688864555008,
//                    1000522688932626432,
//                    1000522689014210560,
//                    1000522689088614400
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 760871503,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522689169133568,
//                    1000522689238728704,
//                    1000522689329963008,
//                    1000522689397886976,
//                    1000522689549934592
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 840817674,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522689621266432,
//                    1000522689699475456,
//                    1000522689779322880,
//                    1000522689852223488,
//                    1000522689919762432
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 316797764,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522690008133632,
//                    1000522690121474048,
//                    1000522690222727168,
//                    1000522690308898816,
//                    1000522690390994944
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 310400976,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522690465796096,
//                    1000522690573844480,
//                    1000522690664833024,
//                    1000522690759061504,
//                    1000522690834681856
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 625340905,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522691055796224,
//                    1000522691133652992,
//                    1000522691213332480,
//                    1000522691288346624,
//                    1000522691356602368
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 914857260,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522691428990976,
//                    1000522691502764032,
//                    1000522691572674560,
//                    1000522691644870656,
//                    1000522691712421888
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 170672843,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522691789303808,
//                    1000522691862265856,
//                    1000522691932307456,
//                    1000522691999227904,
//                    1000522692069576704
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 980139382,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522692141076480,
//                    1000522692217704448,
//                    1000522692294668288,
//                    1000522692378484736,
//                    1000522692457906176
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 571629646,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522692534259712,
//                    1000522692684689408,
//                    1000522692764205056,
//                    1000522692842414080,
//                    1000522692915970048
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 322600956,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522692989358080,
//                    1000522693058035712,
//                    1000522693132693504,
//                    1000522693223927808,
//                    1000522693306626048
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 792419099,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522693385449472,
//                    1000522693464989696,
//                    1000522693530640384,
//                    1000522693604073472,
//                    1000522693671538688
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 222300809,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522693739823104,
//                    1000522693825073152,
//                    1000522693908922368,
//                    1000522693984886784,
//                    1000522694077927424
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 374873260,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522694158491648,
//                    1000522694256062464,
//                    1000522694330687488,
//                    1000522694421934080,
//                    1000522694529052672
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 975131593,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522694603390976,
//                    1000522694692380672,
//                    1000522694776352768,
//                    1000522694842691584,
//                    1000522694922104832
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 536952750,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522695021473792,
//                    1000522695093456896,
//                    1000522695218413568,
//                    1000522695292567552,
//                    1000522695387553792
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 173543241,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522695464452096,
//                    1000522695557390336,
//                    1000522695631843328,
//                    1000522695706796032,
//                    1000522695782461440
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 108122511,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522695860146176,
//                    1000522695968768000,
//                    1000522696038752256,
//                    1000522696117080064,
//                    1000522696214032384
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 534477654,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522696302026752,
//                    1000522696391815168,
//                    1000522696466149376,
//                    1000522696543154176,
//                    1000522696632156160
//                ]
//        }
//        ]
//    }
//]
//        [
//    {
//        "userId": 214740297,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522696719872000,
//                    1000522696794263552,
//                    1000522696872370176,
//                    1000522696946806784,
//                    1000522697021636608
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 133592513,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522697090637824,
//                    1000522697180545024,
//                    1000522697249284096,
//                    1000522697328418816,
//                    1000522697407692800
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 980681836,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522697500422144,
//                    1000522697660858368,
//                    1000522697740943360,
//                    1000522697818578944,
//                    1000522697893920768
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 754458519,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522697988247552,
//                    1000522698080997376,
//                    1000522698159079424,
//                    1000522698233241600,
//                    1000522698308472832
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 657995072,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522698385502208,
//                    1000522698484981760,
//                    1000522698561523712,
//                    1000522698640162816,
//                    1000522698711949312
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 600557792,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522698789109760,
//                    1000522698873917440,
//                    1000522698950516736,
//                    1000522699030630400,
//                    1000522699127660544
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 235016617,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522699202785280,
//                    1000522699280904192,
//                    1000522699362422784,
//                    1000522699431567360,
//                    1000522699507003392
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 108590171,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522699581652992,
//                    1000522699654193152,
//                    1000522699735187456,
//                    1000522699806633984,
//                    1000522699881103360
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 906193820,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522699955982336,
//                    1000522700031680512,
//                    1000522700112547840,
//                    1000522700193198080,
//                    1000522700272095232
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 786172067,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522700354039808,
//                    1000522700429209600,
//                    1000522700517777408,
//                    1000522700605599744,
//                    1000522700683972608
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 452676101,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522700765831168,
//                    1000522700840554496,
//                    1000522700915326976,
//                    1000522700990058496,
//                    1000522701071212544
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 405228404,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522701155835904,
//                    1000522701229969408,
//                    1000522701315629056,
//                    1000522701403017216,
//                    1000522701521453056
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 180302737,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522701645025280,
//                    1000522701739806720,
//                    1000522701844393984,
//                    1000522701945495552,
//                    1000522702026104832
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 166419029,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522702106333184,
//                    1000522702201647104,
//                    1000522702334562304,
//                    1000522702423379968,
//                    1000522702511308800
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 854428508,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522702736801792,
//                    1000522702816247808,
//                    1000522702888669184,
//                    1000522702963032064,
//                    1000522703036321792
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 797725136,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522703108255744,
//                    1000522703188930560,
//                    1000522703280779264,
//                    1000522703434047488,
//                    1000522703512899584
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 753121610,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522703592194048,
//                    1000522703673122816,
//                    1000522703811801088,
//                    1000522703974674432,
//                    1000522704106176512
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 702558521,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522704197996544,
//                    1000522704288256000,
//                    1000522704446885888,
//                    1000522704542826496,
//                    1000522704627486720
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 660126853,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522704717725696,
//                    1000522704803164160,
//                    1000522704915591168,
//                    1000522705020805120,
//                    1000522705130348544
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 641726514,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522705231949824,
//                    1000522705338417152,
//                    1000522705436225536,
//                    1000522705546436608,
//                    1000522705620344832
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 592653162,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522705703211008,
//                    1000522705784188928,
//                    1000522705875832832,
//                    1000522705962561536,
//                    1000522706064252928
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 501365506,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522706177449984,
//                    1000522706258731008,
//                    1000522706466115584,
//                    1000522706567143424,
//                    1000522706709667840
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 170851248,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522706811047936,
//                    1000522706897735680,
//                    1000522706979323904,
//                    1000522707071299584,
//                    1000522707151339520
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 850099550,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522707226447872,
//                    1000522707310362624,
//                    1000522707389112320,
//                    1000522707465064448,
//                    1000522707630145536
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 708638520,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522707715305472,
//                    1000522707790368768,
//                    1000522707872256000,
//                    1000522707954196480,
//                    1000522708040392704
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 554657155,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522708138000384,
//                    1000522708213542912,
//                    1000522708294672384,
//                    1000522708368637952,
//                    1000522708447592448
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 410231689,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522708531236864,
//                    1000522708623478784,
//                    1000522708696334336,
//                    1000522708837564416,
//                    1000522708911652864
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 372092401,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522709035720704,
//                    1000522709123452928,
//                    1000522709224075264,
//                    1000522709302480896,
//                    1000522709370871808
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 344353713,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522709449678848,
//                    1000522709517983744,
//                    1000522709596647424,
//                    1000522709675773952,
//                    1000522709757267968
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 214470167,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522709848604672,
//                    1000522710022914048,
//                    1000522710109167616,
//                    1000522710196133888,
//                    1000522710273806336
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 168662081,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522710377603072,
//                    1000522710491680768,
//                    1000522710569938944,
//                    1000522710653308928,
//                    1000522710725857280
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 538814981,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522710814707712,
//                    1000522710893785088,
//                    1000522710977802240,
//                    1000522711069753344
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 438376136,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522711147245568,
//                    1000522711230066688,
//                    1000522711320748032,
//                    1000522711391154176
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 429600201,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522711492481024,
//                    1000522711585472512,
//                    1000522711700021248,
//                    1000522711852212224
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 874853139,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522711931101184,
//                    1000522712013701120,
//                    1000522712089837568,
//                    1000522712175947776
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 662677248,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522712253599744,
//                    1000522712322105344,
//                    1000522712399147008,
//                    1000522712469024768
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 216401563,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522712545984512,
//                    1000522712623030272,
//                    1000522712701652992
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 903973895,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522712773005312,
//                    1000522712839622656,
//                    1000522712925921280
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 210042916,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522713008689152,
//                    1000522713086926848,
//                    1000522713163882496
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 334824088,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522713379655680,
//                    1000522713456275456,
//                    1000522713538498560
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 965975592,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522713609367552,
//                    1000522713692233728,
//                    1000522713772711936
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 805452367,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522713859215360,
//                    1000522713933725696
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 969487510,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714008195072,
//                    1000522714103554048
//                ]
//        }
//        ]
//    }
//]
//        [
//    {
//        "userId": 848076173,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714176602112,
//                    1000522714250190848
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 448330142,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714318614528,
//                    1000522714402635776
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 994581631,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714468921344,
//                    1000522714546204672
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 404528989,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714619940864,
//                    1000522714693799936
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 378259441,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714768211968,
//                    1000522714842251264
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 303958013,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522714921336832,
//                    1000522714997035008
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 239132342,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522715083407360,
//                    1000522715169861632
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 189333389,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522715265212416,
//                    1000522715358457856
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 871312854,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522715435429888,
//                    1000522715513720832
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 710859185,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522715597533184,
//                    1000522715693346816
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 346588410,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522715805515776,
//                    1000522715926847488
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 933091710,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716012589056
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 876529977,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716090814464
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 876978918,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716174020608
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 720809745,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716245327872
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 574152486,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716327256064
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 454379767,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716398813184
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 435525556,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716477136896
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 336006483,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716625715200
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 240914390,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716705067008
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 106245077,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716785369088
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 850535858,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716857937920
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 844328481,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522716967198720
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 650386048,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717044121600
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 587736639,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717128761344
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 578701779,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717208682496
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 555022978,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717285527552
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 538062248,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717360029696
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 475652162,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717469429760
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 381776122,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717544103936
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 312932882,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717627396096
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 302873056,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717718130688
//                ]
//        }
//        ]
//    }
//]
//        [
//    {
//        "userId": 263351892,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717802074112
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 960188634,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717892128768
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 924013949,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522717992116224
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 903651651,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718083284992
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 662027632,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718201880576
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 603289501,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718309859328
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 539728977,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718408433664
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 347445195,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718556221440
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 273886259,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718682513408
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 263382668,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718779965440
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 251088301,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718867275776
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 123435124,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522718971015168
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 112096123,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719109324800
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 980154387,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719212171264
//                ]
//        }
//        ]
//    }
//]
//        [
//    {
//        "userId": 971553396,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719286087680
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 957539465,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719380324352
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 931943139,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719484096512
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 917954598,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719609479168
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 875244476,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719686541312
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 818419387,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719754063872
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 780785785,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719830745088
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 742652041,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522719975292928
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 737750920,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720066887680
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 696935665,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720166334464
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 661687620,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720246284288
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 597901896,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720476831744
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 562395423,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720552005632
//                ]
//        }
//        ]
//    }
//]
//
//        [
//    {
//        "userId": 552167398,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720619794432
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 531487011,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720694489088
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 529283703,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720802807808
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 526712396,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720918777856
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 522742048,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522720999751680
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 516406647,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721074581504
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 515287518,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721228427264
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 447290550,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721303760896
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 446216449,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721386913792
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 433976345,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721455316992
//                ]
//        }
//        ]
//    },
//    {
//        "userId": 355299119,
//            "airdropType": 0,
//            "recordItems": [
//        {
//            "seriesId": 3,
//                "seriesThemeInfoId": 1000491126767976448,
//                "seriesThemeId": [
//            1000522721527181312
//                ]
//        }
//        ]
//    },


}
