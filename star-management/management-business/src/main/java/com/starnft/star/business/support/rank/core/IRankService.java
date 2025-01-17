package com.starnft.star.business.support.rank.core;

import com.starnft.star.business.support.rank.model.RankDefinition;
import com.starnft.star.business.support.rank.model.RankItemMetaData;
import com.starnft.star.business.support.rank.model.RankingsItem;

import java.util.List;

public interface IRankService {
//    /**
//     * 创建一个排行，{@code createRank(rankName , 1)}
//     * @param rankName 排行的名字，不能重复
//     * @return 如排行已经存在 返回false
//     */
//    public boolean createRank(String rankName);
    /**
     * 创建一个多字段排行
     * @param rankName 排行的名字，不能重复
     *
     * @return 如排行已经存在 返回false
     */
    public boolean createRank(String rankName, RankDefinition rankDefinition);

    public void updateRank(String rankName, RankDefinition rankDefinition);
    /**
     * 删除一个排行
     * @param rankName 排行的名字
     */
    public void deleteRank(String rankName);
    /**
     * 删除所有的排行
     * 在系统要停止的时候调用，等待剩下的排行完成当前工作并正常结束
     */
    public void deleteAllRank();
    /**
     * 判断一个排行是否存在
     * @param rankName 排行的名字
     * @return
     */
    public boolean hasRank(String rankName);
    /**
     * 设置一个数据，如果该id存在，替换为当前数据
     * @param rankName 排行的名字
     * @param value 数据
     * @return 如果存在之前的值，返回之前的值，否则，返回-1
     */
    public Double put(String rankName, String  key, double value, RankItemMetaData rankItemMetaData);

    public Double validPut(String rankName, String key, double value, RankItemMetaData rankItemMetaData);
    /**
     * 设置一个数据，如果该id存在，替换为当前数据
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @param value 数据，必须和对应对应的排行字段数相同
     * @return 如果存在之前的值，返回之前的值，否则，返回null
     */
    public long[] put(String rankName,int id , long... value);
    /**
     * 设置一个数据，如果该id存在，则不设置
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @param value 数据
     * @return 如果存在之前的值，返回之前的值，否则，返回-1
     */
    public long putIfAbsent(String rankName,int id , long value);
    /**
     * 设置一个数据，如果该id存在，则不设置
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @param value 数据，必须和对应对应的排行字段数相同
     * @return 如果存在之前的值，返回之前的值，否则，返回null
     */
    public long[] putIfAbsent(String rankName,int id , long... value);
    /**
     * 根据排行字段更新数据，需要注意，这里的数据是已经存在的数据，否则，报数据不存在异常
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @param field 所修改值的字段
     * @param value 所修改的值
     * @return 原来该字段上的值
     */
    public long putByField(String rankName,int id ,int field,long value);
    /**
     * 删除一个数据
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @return 返回值，不存在返回null
     */
    public long[] delete(String rankName,int id);
    /**
     * 是否存在某玩家的排行数据
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @return
     */
    public boolean has(String rankName,int id);
    /**
     * 查询一个玩家的排行
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @return 对应id玩家的排行名次，没有或查询失败返回-1
     */
    public Long getRankNum(String rankName,Integer id);
    /**
     * 根据id查询一个玩家的排行数据
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @return {@code RankData}
     */
    public RankItemMetaData getRankDataById(String rankName, int id);
    /**
     * 查询某排行的玩家id
     * @param rankName 排行的名字
     * @param rankNum 排行的名次，从0开始
     * @return 对应名次玩家的id，没有或失败则返回-1
     */
    public int getRankId(String rankName,int rankNum);
    /**
     * 根据排行名次获取玩家排行数据
     * @param rankName 排行的名字
     * @param rankNum 排行的名词
     * @return {@code RankData}
     */
    public RankItemMetaData getRankDataByRankNum(String rankName, int rankNum);
    /**
     * 分页查询排行数据
     * @param rankName 排行的名字
     * @param page 页数，从0开始
     * @param pageSize 每一页的大小
     * @return {@code RankData}
     */
//    public List<RankItemMetaData> getRankDatasByPage(String rankName, int page, int pageSize);
    /**
     * 获取用户及其前后几个用户的排行数据
     * @param rankName 排行的名字
     * @param id 数据提供者的id
     * @param beforeNum 获取的前面用户个数
     * @param afterNum	获取的后面用户个数
     * @return {@code RankData}
     */
    public List<RankItemMetaData> getRankDatasAroundId(String rankName, int id, int beforeNum, int afterNum);

    public List<RankingsItem> getRankDatasByPage(String rankName, int page, int pageSize);
}
