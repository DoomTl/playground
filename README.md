#

32 3 2
16 8 16
2 7

res 3

int getLeastVmNum(int cpuCore, int[] serviceA, int[] serviceB)

# ai
``` java
import java.util.PriorityQueue;

public class Solution {
    int getLeastVmNum(int cpuCore, int[] serviceA, int[] serviceB) {
		// serviceA 使用最小堆
		PriorityQueue<Integer> littleHeap = new PriorityQueue<>(serviceA.length, (x, y) -> x - y);
		// serviceB 使用最大堆（通过反向排序来模拟最大堆）
		PriorityQueue<Integer> bigHeap = new PriorityQueue<>(serviceB.length, (x, y) -> y - x);

        // 将任务添加到对应的堆中
        for (int i : serviceA) {
            littleHeap.add(i);
        }
        for (int i : serviceB) {
            bigHeap.add(i);
        }

        int count = 0; // 用于记录需要的虚拟机数量

        while (!littleHeap.isEmpty() || !bigHeap.isEmpty()) {
            // 如果 littleHeap 为空，则从 bigHeap 中取出最大任务并移到 littleHeap
            if (littleHeap.isEmpty()) {
                littleHeap.add(bigHeap.poll());
            }
            // 如果 bigHeap 为空，则处理 littleHeap 中的任务
            if (bigHeap.isEmpty()) {
                littleHeap.poll(); // 处理 littleHeap 中的任务
                count++;
            }
            // 如果两个堆都有任务
            else {
                int littleTask = littleHeap.peek();
                int bigTask = bigHeap.peek();
                
                // 尝试将 littleHeap 中的最小任务与 bigHeap 中的最大任务组合
                if (littleTask + bigTask <= cpuCore) {
                    // 如果能组合，处理两个任务
                    littleHeap.poll();
                    bigHeap.poll();
                } else {
                    // 如果不能组合，处理 littleHeap 中的任务
                    littleHeap.poll();
                }
                count++;
            }
        }
        return count; // 返回所需的虚拟机数量
    }
}
```

# 排序加双指针
``` java
import java.util.Arrays;

public class Solution {
    int getLeastVmNum(int cpuCore, int[] serviceA, int[] serviceB) {
        // 对两个服务的任务进行排序
        Arrays.sort(serviceA);
        Arrays.sort(serviceB);

        int count = 0;  // 用于记录虚拟机数量
        int i = 0, j = serviceB.length - 1;

        // 双指针方法
        while (i < serviceA.length || j >= 0) {
            if (i < serviceA.length && j >= 0 && serviceA[i] + serviceB[j] <= cpuCore) {
                // 如果 serviceA[i] 和 serviceB[j] 能放在一台虚拟机里，就一起处理
                i++;
                j--;
            } else if (i < serviceA.length) {
                // 如果不能组合，处理 serviceA[i]
                i++;
            } else if (j >= 0) {
                // 如果不能组合，处理 serviceB[j]
                j--;
            }
            // 每次处理一个任务或者任务对都需要一台虚拟机
            count++;
        }

        return count;
    }
}

```

# me
``` java
import java.util.PriorityQueue;

public class Solution {
	int getLeastVmNum(int cpuCore, int[] serviceA, int[] serviceB) {
		PriorityQueue<Integer> littleHeap = new PriorityQueue<>((x, y) -> x - y);
		PriorityQueue<Integer> bigHeap = new PriorityQueue<>((x, y) -> y - x);
		for (int i : serviceA) {
			littleHeap.add(i);
		}
		for (int i : serviceB) {
			bigHeap.add(i);
		}

		int count = 0;
		while (true) {
			if (littleHeap.isEmpty() && !bigHeap.isEmpty()) {
				littleHeap.add(bigHeap.poll());
			}
			if (!littleHeap.isEmpty() && bigHeap.isEmpty()) {
				littleHeap.poll();
				count++;
			}
			if (!littleHeap.isEmpty() && !bigHeap.isEmpty()) {
				if (littleHeap.peek() + bigHeap.peek() <= cpuCore) {
					littleHeap.poll();
					bigHeap.poll();
				} else {
					littleHeap.poll();
				}
				count++;
			} else {
				break;
			}
		}
		return count;
	}
}
```



# mine
```java
import java.util.List;


public class Solution {
	boolean result;
	List<String> matrix;
	public int count(List<String> matrix, List<String> words) {
		this.matrix = matrix;
		int count = 0;
		for (String word : words) {
			result = false;
			if (isAvailable(word)) {
				count++;
			}
		}
		return count;
	}

	private boolean isAvailable(String word) {
		int row = matrix.size();
		int column = matrix.get(0).length();
		boolean[][] used = new boolean[row][column];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				// 矩阵中可能有多个有效的起点 一旦匹配到 就跳过其他起点
				if (result) {
					break;
				}
				dfs(i, j, 0, word, used);
			}
		}
		return result;
	}

	private void dfs(int i, int j, int index, String word, boolean[][] used) {
		// 判断边界条件
		if (i < 0 || i >= matrix.size() || j < 0 || j >= matrix.get(0).length()) {
			return;
		}
		char charInMatrix = matrix.get(i).charAt(j);
		// 矩阵中字符未使用且能匹配
		if (!used[i][j] && ((charInMatrix == word.charAt(index)) || ('?' == charInMatrix))) {
			if (index == word.length() - 1) {
				result = true;
			} else {
				used[i][j] = true;
				dfs(i - 1, j, index + 1, word, used);
				dfs(i, j - 1, index + 1, word, used);
				dfs(i + 1, j, index + 1, word, used);
				dfs(i, j + 1, index + 1, word, used);
				used[i][j] = false;
			}
		}
	}
}
```

# dfs
``` java
import java.util.List;

public class Solution {

	public int count(List<String> matrix, List<String> words) {
		int count = 0;
		for (String word : words) {
			if (isAvailable(matrix, word)) {
				count++;
			}
		}
		return count;
	}

	private boolean isAvailable(List<String> matrix, String word) {
		int row = matrix.size();
		int column = matrix.get(0).length();
		boolean[][] used = new boolean[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (dfs(i, j, 0, word, matrix, used)) {
					return true;  // 如果找到了单词，直接返回
				}
			}
		}
		return false;
	}

	private boolean dfs(int i, int j, int index, String word, List<String> matrix, boolean[][] used) {
		// 边界检查
		if (i < 0 || i >= matrix.size() || j < 0 || j >= matrix.get(0).length()) {
			return false;
		}
		char charInMatrix = matrix.get(i).charAt(j);
		// 判断当前位置的字符是否匹配单词的当前字符，或者是通配符 '?'
		if (!used[i][j] && (charInMatrix == word.charAt(index) || charInMatrix == '?')) {
			if (index == word.length() - 1) {
				return true;  // 如果匹配完了整个单词，返回 true
			} else {
				used[i][j] = true;
				// 尝试四个方向进行递归搜索
				boolean found = dfs(i - 1, j, index + 1, word, matrix, used) ||
						dfs(i, j - 1, index + 1, word, matrix, used) ||
						dfs(i + 1, j, index + 1, word, matrix, used) ||
						dfs(i, j + 1, index + 1, word, matrix, used);
				used[i][j] = false;  // 回溯
				return found;
			}
		}
		return false;
	}
}

```
# bfs
``` java
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public int count(List<String> matrix, List<String> words) {
        int count = 0;
        for (String word : words) {
            if (isAvailable(matrix, word)) {
                count++;
            }
        }
        return count;
    }

    private boolean isAvailable(List<String> matrix, String word) {
        int row = matrix.size();
        int column = matrix.get(0).length();
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (bfs(i, j, word, matrix)) {
                    return true;  // 一旦找到单词，直接返回
                }
            }
        }
        return false;
    }

    private boolean bfs(int startX, int startY, String word, List<String> matrix) {
        int row = matrix.size();
        int column = matrix.get(0).length();
        
        if (matrix.get(startX).charAt(startY) != word.charAt(0) && matrix.get(startX).charAt(startY) != '?') {
            return false;
        }

        // 队列保存当前位置及当前匹配的索引
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] used = new boolean[row][column];  // 用于标记已经访问过的格子
        queue.offer(new int[]{startX, startY, 0});  // 存储格式：[x, y, word索引]
        used[startX][startY] = true;  // 标记起点已访问

        // 方向数组：上、下、左、右
        int[] directions = {-1, 0, 1, 0, 0, -1, 0, 1};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];
            int index = curr[2];

            // 如果已经匹配到单词的末尾，返回true
            if (index == word.length() - 1) {
                return true;
            }

            // 扩展到四个方向
            for (int i = 0; i < 4; i++) {
                int nx = x + directions[i * 2];
                int ny = y + directions[i * 2 + 1];
                if (nx >= 0 && nx < row && ny >= 0 && ny < column && !used[nx][ny]) {
                    char currentChar = matrix.get(nx).charAt(ny);
                    // 判断当前字符是否匹配
                    if (currentChar == word.charAt(index + 1) || currentChar == '?') {
                        used[nx][ny] = true;
                        queue.offer(new int[]{nx, ny, index + 1});
                    }
                }
            }
        }
        return false;
    }
}

```
