package com.luckychacha.elasticsearch.service.leetcode;

public interface LeetCodeService {
    String threeSum(int[] nums);

    Integer maxAreaOfIsland(int[][] grid);

    int rotation(int[] nums, int target);

    Integer findLengthOfLCIS(int[] nums);

    Integer findKthLargest(int[] nums, int target);

    Integer longestConsecutive(int[] nums);

    String getPermutation(int n, int k);

    Integer findCircleNum(int[][] grid);
}
