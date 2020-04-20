package com.luckychacha.elasticsearch.api.controller.leetcode;

import com.luckychacha.elasticsearch.service.leetcode.LeetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leetcode")
public class LeetCodeController {

    private LeetCodeService leetCodeService;

    @Autowired
    public LeetCodeController(LeetCodeService leetCodeService) {
        this.leetCodeService = leetCodeService;
    }

    @GetMapping("threeSum")
    public String threeSum(@PathVariable("nums") int[] nums) {
        return leetCodeService.threeSum(nums);
    }

    @PostMapping("maxAreaOfIsland")
    public Integer maxAreaOfIsland(@RequestBody int[][] grid) {
        return leetCodeService.maxAreaOfIsland(grid);
    }


    @PostMapping("rotation/{target}")
    public Integer rotation(@RequestBody int[] nums, @PathVariable("target") int target) {
        return leetCodeService.rotation(nums, target);
    }
}
