package com.luckychacha.elasticsearch.service.leetcode.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckychacha.elasticsearch.model.entity.leetcode.maxAreaOfIsland.Location;
import com.luckychacha.elasticsearch.service.leetcode.LeetCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Slf4j
@Service
public class LeetCodeServiceImpl implements LeetCodeService {

    private int[] nums;
    private int target;

    /**
     * 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *
     *
     *
     * 示例：
     *
     * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     *
     * 满足要求的三元组集合为：
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     * @return String json结果
     */
    @Override
    public String threeSum(int[] nums) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length -2; i++) {
            if (nums[i] > 0) {
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;//当前元素的下一个元素
            int k = nums.length -1;//末尾元素

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    while(j < k && nums[j] == nums[++j]);
                } else if (sum > 0) {
                    while(j < k && nums[k] == nums[--k]);
                } else {
                    res.add(new ArrayList<Integer>(Arrays.asList(nums[i],nums[j],nums[k])));
                    while(j < k && nums[j] == nums[++j]);
                    while(j < k && nums[k] == nums[--k]);
                }

            }
        }
        try {
            return objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    private boolean[][] visited;//记录是否被访问
    /**
     * 岛屿的最大面积
     * 给定一个包含了一些 0 和 1 的非空二维数组 grid 。
     *
     * 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
     *
     * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
     *
     *
     *
     * 示例 1:
     *
     * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
     *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
     *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
     *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
     *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
     *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
     *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
     *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
     * 对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。
     *
     * 示例 2:
     *
     * [[0,0,0,0,0,0,0,0]]
     * 对于上面这个给定的矩阵, 返回 0。
     *
     *
     *
     * 注意: 给定的矩阵grid 的长度和宽度都不超过 50。
     * @param grid 矩阵
     * @return String
     */
    @Override
    public Integer maxAreaOfIsland(int[][] grid) {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        if (grid.length <= 0 || grid[0].length <= 0) {
            return null;
        }
        visited = new boolean[grid.length][grid[0].length];
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1
                        && !visited[i][j]) {
                    result = Math.max(result, dfs(grid, i, j));
                }

            }
        }


        return result;
    }

    /**
     * 搜索旋转排序数组
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     *
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     *
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     *
     * 你可以假设数组中不存在重复的元素。
     *
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 示例 1:
     *
     * 输入: nums = [4,5,6,7,0,1,2], target = 0
     * 输出: 4
     * 示例 2:
     *
     * 输入: nums = [4,5,6,7,0,1,2], target = 3
     * 输出: -1
     */

    @Override
    public int rotation(int[] nums, int target) {
        this.nums = nums;
        this.target = target;

        int leftIndex = 0;
        int rightIndex = nums.length - 1;

        /*
        异常处理
         */
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }
        /*
        1.找出数组中的旋转点【最小值】
        2.如果目标值大于等于左边，那么从 0 到 旋转点-1 中寻找，否则，在 旋转点 到 最右 中找
         */
        int lowestIndex = findLowestIndex(leftIndex, rightIndex);
        if (nums[leftIndex] > target || lowestIndex == 0) {
            return binarySearch(lowestIndex, rightIndex);
        }
        return binarySearch(leftIndex, lowestIndex - 1);
    }

    /**
     * 最长连续递增序列
     * 给定一个未经排序的整数数组，找到最长且连续的的递增序列。
     *
     * 示例 1:
     *
     * 输入: [1,3,5,4,7]
     * 输出: 3
     * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
     * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
     * 示例 2:
     *
     * 输入: [2,2,2,2,2]
     * 输出: 1
     * 解释: 最长连续递增序列是 [2], 长度为1。
     * 注意：数组长度不会超过10000。
     * @param nums
     * @return
     */
    @Override
    public Integer findLengthOfLCIS(int[] nums) {
        int max = 1;
        int tmp = 1;

        if (1 >= nums.length) {
            return nums.length;
        }

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i-1]) {
                tmp++;
            } else {
                tmp = 1;
            }
            max = Math.max(tmp, max);
        }

        return max;
    }

    /**
     * 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * 示例 1:
     *
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * 示例 2:
     *
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     * 说明:
     *
     * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
     * @param nums 目标数组
     * @param k 第 K 大的元素
     * @return Integer
     */
    @Override
    public Integer findKthLargest(int[] nums, int k) {

        if (k < 0 || k > nums.length) {
            return 0;
        }
//
//        // 1.排序
//        int tmp;
//        for (int i = 0; i < nums.length-1; i++) {
//            for (int j = i+1; j < nums.length; j++) {
//                if (nums[i] > nums[j]) {
//                    tmp = nums[i];
//                    nums[i] = nums[j];
//                    nums[j] = tmp;
//                }
//            }
//        }
//        // 2.总长度为 n，第 k 个元素，正序就是 (k - 1)，逆序 (n-k)
//        return nums[nums.length - k];


        // 方法 2，使用 堆

        // 创建升序堆
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
        for (int i : nums) {
            heap.add(i);
            if (heap.size() > k) {
                // 保持堆的深度始终与要查看的第 k 个元素一致。
                heap.poll();
            }
        }
        return heap.poll();

    }

    private int binarySearch(int leftIndex, int rightIndex) {
        while (leftIndex <= rightIndex) {
            int mid = leftIndex + (rightIndex - leftIndex) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (target < nums[mid]) {
                rightIndex = mid - 1;
            } else {
                leftIndex = mid + 1;
            }
        }
        return -1;
    }

    private int findLowestIndex(int leftIndex, int rightIndex) {
        if (nums[leftIndex] < nums[rightIndex]) {
            // 顺序数组
            return 0;
        }

        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (nums[midIndex] > nums[midIndex + 1]) {
                return midIndex + 1;
            }
            if (nums[midIndex] > nums[leftIndex]) {
                leftIndex = midIndex + 1;
            } else {
                rightIndex = midIndex;
            }
        }
        return 0;
    }

    private int dfs(int[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] != 1 || visited[x][y]) {
            return 0;
        }
        visited[x][y] = true;
        return 1 + dfs(grid, x, y-1) + dfs(grid, x, y+1) + dfs(grid, x-1, y) + dfs(grid, x+1, y);
    }
}
