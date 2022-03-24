package com.dtm.mallproject.util;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.mapper.ClassificationMapper;
import com.dtm.mallproject.mapper.UserMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.ClassificationDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.BookExcelVO;
import com.dtm.mallproject.pojo.vo.UserExcelVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/23 11:24
 * @description : 操作 Excel 的帮助类
 */
@Component
public class ExcelUtil {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private ClassificationMapper classificationMapper;
    @Resource
    private UserMapper userMapper;

    private Map<String, Object> getBookData(BookExcelVO bookExcelVO){
        // 查询图书信息
        BookDO book = bookMapper.selectById(bookExcelVO.getBookId());

        // 获取折扣信息
        Double bookDiscount = book.getDiscount();
        String discount = "";
        if (bookDiscount < 1){
            discount = new DecimalFormat("0.0").format(bookDiscount*10) + "折";
        }

        // 获取状态信息
        Integer bookState = book.getState();
        String state = "";
        if (bookState == 0){
            state = "不展示";
        }
        switch (bookState) {
            case 1:
                state = "最新上架";
                break;
            case 2:
                state = "独家畅品";
                break;
            case 3:
                state = "重点推荐";
                break;
            case 4:
                state = "好评发售";
                break;
            default: break;
        }

        // 查询类别的中文
        QueryWrapper<ClassificationDO> qw = new QueryWrapper<>();
        qw.select("classification_mean").eq("classification_code",book.getClassificationCode());
        ClassificationDO classification = classificationMapper.selectOne(qw);

        BookExcelVO bookData = BookExcelVO.builder()
                .bookId(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .press(book.getPress())
                .price(book.getPrice().toString())
                .discount(discount)
                .state(state)
                .classification(classification.getClassificationMean())
                .build();

        Map<String, Object> data = new HashMap<>(1);
        // 基本信息
        String url = "D:\\Codes\\Java\\Mall\\mall-vue\\public\\static\\img\\book\\";
        String imgName = book.getImg().substring(book.getImg().lastIndexOf("/"));
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setRowspan(10);
        imageEntity.setColspan(3);
        imageEntity.setUrl(url+imgName);
        data.put("img",imageEntity);
        data.put("bookName",bookData.getBookName());
        data.put("id",bookData.getBookId());
        data.put("author",bookData.getAuthor());
        data.put("press",bookData.getPress());
        data.put("price",bookData.getPrice());
        data.put("discount",bookData.getDiscount());
        data.put("state",bookData.getState());
        data.put("classification",bookData.getClassification());
        // 销售量
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 13; j++) {
                data.put("sales"+i+"-"+j,bookExcelVO.getSalesVolume()[i-1][j-1]);
                data.put("col"+i+"-"+j,bookExcelVO.getCollection()[i-1][j-1]);
                data.put("click"+i+"-"+j,bookExcelVO.getClicks()[i-1][j-1]);
            }
        }
        // 评价
        int total = 0;
        Integer[] rate = bookExcelVO.getRate();
        for (Integer integer : rate) {
            total += integer;
        }
        for (int i = 0; i < rate.length; i++) {
            String percentage = new DecimalFormat("0.00").format((float) rate[i]/total*100);
            data.put("rate"+(i+1),rate[i] + " (" + percentage + "%)");
        }

        return data;
    }

    private TemplateExportParams getBookTemplateExportParams(){
        String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("excelTemplate/BookSalesTemplate.xlsx")).getPath();
        return new TemplateExportParams(path);
    }

    public Workbook exportBookExcel(BookExcelVO bookExcelVO){
        // 获取数据
        Map<String, Object> data = getBookData(bookExcelVO);

        // 获取模板参数
        TemplateExportParams params = getBookTemplateExportParams();

        // 返回工作簿
        return ExcelExportUtil.exportExcel(params, data);
    }

    private Map<String, Object> getUserData(UserExcelVO userExcelVO){
        // 查询用户信息
        UserDO user = userMapper.selectById(userExcelVO.getUserId());

        Map<String, Object> data = new HashMap<>(1);
        // 基本信息
        String url = "D:\\Codes\\Java\\Mall\\mall-vue\\src\\assets\\img\\icon\\user.png";
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setRowspan(10);
        imageEntity.setColspan(3);
        imageEntity.setUrl(url);
        data.put("icon",imageEntity);
        data.put("userName",user.getUserName());
        data.put("id",userExcelVO.getUserId());
        data.put("shippingAddress",user.getShippingAddress());
        data.put("phoneNumber",user.getPhoneNumber());
        data.put("email",user.getEmail());
        data.put("consumption",user.getConsumption()+"(元)");
        data.put("lastLoginTime",user.getLastLoginDate());
        // 消费信息
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 13; j++) {
                data.put("view"+i+"-"+j,userExcelVO.getPageviews()[i-1][j-1]);
                data.put("consumptions"+i+"-"+j,userExcelVO.getPageviews()[i-1][j-1]);
            }
        }
        // 指标信息
        for (int i = 0; i < userExcelVO.getMetrics().length; i++) {
            data.put("metrics"+(i+1),userExcelVO.getMetrics()[i]);
        }

        return data;
    }

    private TemplateExportParams getUserTemplateExportParams(){
        String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("excelTemplate/UserConsumptionTemplate.xlsx")).getPath();
        return new TemplateExportParams(path);
    }

    public Workbook exportUserExcel(UserExcelVO userExcelVO){
        // 获取数据
        Map<String, Object> data = getUserData(userExcelVO);

        // 获取模板参数
        TemplateExportParams params = getUserTemplateExportParams();

        // 返回工作簿
        return ExcelExportUtil.exportExcel(params, data);
    }
}
