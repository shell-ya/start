package com.starnft.star.business.service.impl;

import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.vo.StarNftThemeNumberVo;
import com.starnft.star.business.mapper.StarNftThemeNumberMapper;
import com.starnft.star.business.service.IStarNftThemeNumberService;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.StringUtils;
import com.starnft.star.common.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

/**
 * 主题编号Service业务层处理
 *
 * @author shellya
 * @date 2022-06-03
 */
@Service
public class StarNftThemeNumberServiceImpl implements IStarNftThemeNumberService
{
    private static final Logger log = LoggerFactory.getLogger(StarNftThemeNumberServiceImpl.class);
    @Autowired
    private StarNftThemeNumberMapper starNftThemeNumberMapper;

    @Autowired
    protected Validator validator;
    /**
     * 查询主题编号
     *
     * @param id 主题编号主键
     * @return 主题编号
     */
    @Override
    public StarNftThemeNumber selectStarNftThemeNumberById(Long id)
    {
        return starNftThemeNumberMapper.selectStarNftThemeNumberById(id);
    }

    /**
     * 查询主题编号列表
     *
     * @param starNftThemeNumber 主题编号
     * @return 主题编号
     */
    @Override
    public List<StarNftThemeNumber> selectStarNftThemeNumberList(StarNftThemeNumber starNftThemeNumber)
    {
        return starNftThemeNumberMapper.selectStarNftThemeNumberList(starNftThemeNumber);
    }

    /**
     * 新增主题编号
     *
     * @param starNftThemeNumber 主题编号
     * @return 结果
     */
    @Override
    public int insertStarNftThemeNumber(StarNftThemeNumber starNftThemeNumber,String userId)
    {
        Long numberId = SnowflakeWorker.generateId();
        starNftThemeNumber.setId(numberId);
        starNftThemeNumber.setCreateBy(userId);
        starNftThemeNumber.setUpdateBy(userId);
        starNftThemeNumber.setCreateAt(new Date());
        starNftThemeNumber.setUpdateAt(new Date());
        return starNftThemeNumberMapper.insertStarNftThemeNumber(starNftThemeNumber);
    }

    /**
     * 修改主题编号
     *
     * @param starNftThemeNumber 主题编号
     * @return 结果
     */
    @Override
    public int updateStarNftThemeNumber(StarNftThemeNumber starNftThemeNumber)
    {
        return starNftThemeNumberMapper.updateStarNftThemeNumber(starNftThemeNumber);
    }

    /**
     * 批量删除主题编号
     *
     * @param ids 需要删除的主题编号主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeNumberByIds(Long[] ids)
    {
        return starNftThemeNumberMapper.deleteStarNftThemeNumberByIds(ids);
    }

    /**
     * 删除主题编号信息
     *
     * @param id 主题编号主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeNumberById(Long id)
    {
        return starNftThemeNumberMapper.deleteStarNftThemeNumberById(id);
    }

    @Override
    public String importThemeNumber(List<StarNftThemeNumber> themeNumberList, Long themeId, String operName) {
        if (StringUtils.isNull(themeNumberList) || themeNumberList.size() == 0)
        {
            throw new ServiceException("导入主题作品数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StarNftThemeNumber themeNumber : themeNumberList)
        {

            try
            {
                // 验证是否存在这个作品
                StarNftThemeNumber n = starNftThemeNumberMapper.selectStarNftThemeNumberById(themeNumber.getId());
                if (StringUtils.isNull(n))
                {
                    BeanValidators.validateWithException(validator, themeNumber);
                    themeNumber.setSeriesThemeInfoId(themeId);
//                    Long themeNumberId = SnowflakeWorker.generateId();
//                    themeNumber.setCreateAt(new Date());
//                    themeNumber.setUpdateAt(new Date());
//                    themeNumber.setCreateBy(operName);
//                    themeNumber.setUpdateBy(operName);
//                    themeNumber.setId(themeNumberId);
                    this.insertStarNftThemeNumber(themeNumber,operName);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、编号 " + themeNumber.getThemeNumber() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、编号 " + themeNumber.getThemeNumber() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + themeNumber.getThemeNumber() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }

    @Override
    public List<StarNftThemeNumberVo> selectStarNftThemeNumberVoList(StarNftThemeNumberVo starNftThemeNumber) {

        return this.starNftThemeNumberMapper.selectStarNftThemeNumberVoList(starNftThemeNumber);
    }
}
