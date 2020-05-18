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
        // 1.创建前 k 个元素的小顶堆
        buildHeap(nums, k);
        // 2.将剩下的元素和堆顶部的元素进行比较，如果比他小，跳过，比他大，一次堆话排序
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > nums[0]) {
                swap(nums, 0, i);
                heapify(nums, k, 0);
            }
        }
        return nums[0];
    }

    private void buildHeap(int[] nums, int k) {
        // 从最后一个非叶子节点开始创建小顶堆 k/2 -1
        for (int i = k/2 - 1; i >=0; i--) {
            heapify(nums, k, i);
        }
    }

    private void heapify(int[] nums, int k, int i) {
        int min = i;
        while(true) {
            if ( (2*i + 1) < k && nums[2 * i + 1] < nums[i]) {
                min = 2*i + 1;
            }
            if ((2*i + 2) < k && nums[2 * i +2] < nums[min]) {
                min = 2*i + 2;
            }
            if (min == i) {
                break;
            }
            swap(nums, min, i);
            i = min;
        }
    }

    private void swap(int[] nums, int target, int source) {
        int tmp = nums[target];
        nums[target] = nums[source];
        nums[source] = tmp;
    }

    @Override
    public Integer longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int item : nums) {
            if (map.containsKey(item)) {
                continue;
            }

            int left = map.getOrDefault(item - 1, 0);
            int right = map.getOrDefault(item + 1, 0);
            int sum = left + 1 + right;
            max = Math.max(max, sum);

            map.put(item, -1);
            map.put(item - left, sum);
            map.put(item + right, sum);
        }

        return max;
    }

    /**
     * 第k个排列
     * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
     *
     * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * 给定 n 和 k，返回第 k 个排列。
     *
     * 说明：
     *
     * 给定 n 的范围是 [1, 9]。
     * 给定 k 的范围是[1,  n!]。
     * 示例 1:
     *
     * 输入: n = 3, k = 3
     * 输出: "213"
     * 示例 2:
     *
     * 输入: n = 4, k = 9
     * 输出: "2314"
     *
     * @param n
     * @param k
     * @return String
     */
    @Override
    public String getPermutation(int n, int k) {
        /**
         * 1. 生成 0 - (n-1) 的阶乘
         * 2. 生成 0 - (n-1) 的桶，值为 1-n
         * 3. 循环除法，并且移除元素，循环 n 次得到结果
         */

        int[] fac = new int[n];
        fac[0] = 1;
        for (int i= 1; i < n; i++) {
            fac[i] = fac[i-1] * i;
        }
        log.info(String.valueOf(fac.length));
        List<Integer> bucket = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            bucket.add(i+1);
        }
        log.info(String.valueOf(bucket.size()));


        StringBuilder res = new StringBuilder();
        k--;
        for (int i = n-1; i >=0; i--) {
            int index = k / fac[i];
            res.append(bucket.get(index));
            bucket.remove(index);
            log.info("本轮的 k 值：" + k + " 除以：" + String.valueOf(fac[i])  + " 商：" + index + " 余数：" +  k % fac[i]);
            k = k % fac[i];
        }
        return res.toString();
    }

    /**
     * 朋友圈
     * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
     *
     * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
     *
     * 示例 1:
     *
     * 输入:
     * [[1,1,0],
     *  [1,1,0],
     *  [0,0,1]]
     * 输出: 2
     * 说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
     * 第2个学生自己在一个朋友圈。所以返回2。
     * 示例 2:
     *
     * 输入:
     * [[1,1,0],
     *  [1,1,1],
     *  [0,1,1]]
     * 输出: 1
     * 说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。
     * 注意：
     *
     * N 在[1,200]的范围内。
     * 对于所有学生，有M[i][i] = 1。
     * 如果有M[i][j] = 1，则有M[j][i] = 1。
     * @param grid
     * @return int
     */
    @Override
    public Integer findCircleNum(int[][] grid) {
        int size = grid.length;
        int count = 0;
        boolean[] mark = new boolean[size];
        for (int i = 0; i < size; i++) {
            if (mark[i]) {
                continue;
            }
            mark[i] = true;
            count++;
            dfsForCircleNum(grid, i, mark);
        }
        return count;
    }

    private void dfsForCircleNum(int[][] grid, int i, boolean[] mark) {
        int size = grid.length;
        for (int j = 0; j < size; j++) {
            if (mark[j] || grid[i][j] != 1) {
                continue;
            }
            mark[j] = true;
            dfsForCircleNum(grid, j, mark);
        }
    }
}
