package com.zj.examsystem.controller;


import com.zj.examsystem.entity.Test;
import com.zj.examsystem.service.TestService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;


@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/findAllByUserId")
    @ResponseBody
    public Object findAllByUserId(Integer pageno, Integer size, Integer userId) {
        return BaseResponseEntity.ok("", testService.findAll(pageno, size, userId));
    }

    @GetMapping("/findById")
    @ResponseBody
    public Object findById(Integer testId) {
        return BaseResponseEntity.ok("", testService.findById(testId));
    }

    @GetMapping("/findExamTimeByTestId")
    @ResponseBody
    public Object findExamTimeByTestId(Integer userId, Integer testId) {
        return testService.findExamTimeByTestId(userId, testId) ? BaseResponseEntity.ok("", "") : BaseResponseEntity.error(ResponseCode.FAIL,
                "测验次数已达上限");
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save(Test test, Integer[] questionList, String[] shortAnswer, String status) {
        Boolean result = test.getTestId() == null ? testService.saveTest(test, questionList, shortAnswer) : testService.updateTest(test,
                questionList, shortAnswer);
        return result ? BaseResponseEntity.ok(status + "成功", "") : BaseResponseEntity.error(ResponseCode.FAIL,
                status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] testId) {
        int result = testService.deleteTest(testId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) : BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }
}

