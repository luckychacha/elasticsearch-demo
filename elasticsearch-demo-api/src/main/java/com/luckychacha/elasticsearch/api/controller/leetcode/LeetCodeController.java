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


    @PostMapping("findLengthOfLCIS")
    public Integer findLengthOfLCIS(@RequestBody int[] nums) {
        return leetCodeService.findLengthOfLCIS(nums);
    }

    @PostMapping("findKthLargest/{target}")
    public Integer findKthLargest(@RequestBody int[] nums, @PathVariable("target") int target) {
        return leetCodeService.findKthLargest(nums, target);
    }

    @PostMapping("longestConsecutive")
    public Integer longestConsecutive(@RequestBody int[] nums) {
        return leetCodeService.longestConsecutive(nums);
    }

    @PostMapping("getPermutation/{k}")
    public String getPermutation(@RequestBody int n, @PathVariable("k") int k) {
        return leetCodeService.getPermutation(n, k);
    }

    @PostMapping("findCircleNum")
    public Integer findCircleNum(@RequestBody int[][] grid) {
        return leetCodeService.findCircleNum(grid);
    }
}
