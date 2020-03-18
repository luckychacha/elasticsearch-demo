package com.luckychacha.elasticsearch.service.leetcode.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckychacha.elasticsearch.model.entity.leetcode.maxAreaOfIsland.Location;
import com.luckychacha.elasticsearch.service.leetcode.LeetCodeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class LeetCodeServiceImpl implements LeetCodeService {

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

    private int dfs(int[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] != 1 || visited[x][y]) {
            return 0;
        }
        visited[x][y] = true;
        return 1 + dfs(grid, x, y-1) + dfs(grid, x, y+1) + dfs(grid, x-1, y) + dfs(grid, x+1, y);
    }
}
