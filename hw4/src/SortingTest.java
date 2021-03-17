import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	public static void swap(int[] arr, int idx1, int idx2){
		int temp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = temp;
	}

	public static int heap_parent(int idx){return (idx+1)/2-1;}
	public static int heap_left(int idx){return 2*idx+1;}
	public static int heap_right(int idx){return 2*idx+2;}

	// merge sort [start, stop)
	public static void mergeSort(int[] value, int start, int stop){
		if(stop - start <= 1) {return;}
		int mid = (start + stop) / 2;
		mergeSort(value, start, mid);
		mergeSort(value, mid, stop);

		int idx1 = start;
		int idx2 = mid;
		int idx_buf = 0;
		int[] buf = new int[stop-start];
		while ((idx1 < mid) && (idx2 < stop)){
			if(value[idx1]>value[idx2]){
				buf[idx_buf] = value[idx2];
				idx2++;
			}else{
				buf[idx_buf] = value[idx1];
				idx1++;
			}
			idx_buf++;
		}
		while (idx1 < mid){
			buf[idx_buf] = value[idx1];
			idx1++;
			idx_buf++;
		}
		while (idx2 < stop){
			buf[idx_buf] = value[idx2];
			idx2++;
			idx_buf++;
		}

		System.arraycopy(buf, 0, value, start, stop-start);
	}

	// quick sort [start, stop)
	public static void quickSort(int[] value, int start, int stop){
		if(stop-start<=1){return;}

		int pivot = value[start];
		int left = start + 1;
		int right = stop - 1;
		while(left < right){
			if(value[left] > pivot && value[right] < pivot){
				swap(value, left, right);
				left++;
				right--;
			}else if(value[left] <= pivot && value[right] >= pivot) {
				left++;
				right--;
			}else if(value[left] <= pivot){ left++;
			}else if(value[right] >= pivot){ right--; }
		}
		if(value[left]>pivot){ // insert pivot left
			for(int i=start; i<left-1; i++){value[i]=value[i+1];}
			value[left-1] = pivot;
			quickSort(value, start, left-1);
			quickSort(value, left, stop);
		}else{ // insert pivot right
			for(int i=start; i<left; i++){value[i]=value[i+1];}
			value[left] = pivot;
			quickSort(value, start, left);
			quickSort(value, left+1, stop);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		for(int i=1; i< value.length; i++)
			for(int j=0; j<value.length-i; j++)
				if(value[j] > value[j+1]){swap(value, j, j+1);}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		for (int i=1; i<value.length; i++)
			for(int j=i; j>0 && value[j-1] > value[j]; j--){swap(value, j, j-1);}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		//build up maximum heap
		for(int i=1; i<value.length; i++)
			for(int j=i; heap_parent(j) >= 0 && value[j]>value[heap_parent(j)]; j=heap_parent(j)){ swap(value, j, heap_parent(j)); }

		//swap root(maximum) and last one, rearrange the heap
		for(int i=value.length-1; i>1; i--){
			swap(value, 0, i);
			int j = 0;
			while(j<i){
				int temp;
				boolean change_l = false;
				boolean change_r = false;
				if(heap_left(j) < i && value[j]<value[heap_left(j)]){ change_l = true; }
				if(heap_right(j) < i && value[j]<value[heap_right(j)]){ change_r = true; }
				if(change_l && change_r){
					if(value[heap_left(j)] > value[heap_right(j)]){ temp = heap_left(j);
					}else{ temp = heap_right(j);}
				}else if(change_l){ temp = heap_left(j);
				}else if(change_r){ temp = heap_right(j);
				}else{ break; }

				swap(value, j, temp);
				j = temp;
			}
		}

		if(value[0] > value[1]){swap(value, 0, 1);}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		mergeSort(value, 0, value.length);
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		quickSort(value, 0, value.length);
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// save the order or each remainder

		int base = 16;
		for(int order=0; order<8; order++){
			int denom = (int)Math.pow(base, order);
			int[] cnt_radi = new int[2*base]; // because of negative values.
			int[] buf = new int[value.length];

			for(int i=0; i<value.length; i++){
				int remainder = base + (value[i]/denom)%base; // add base to make remainder plus
				cnt_radi[remainder]++;
			}

			// sum up cnt_radi to find order of num in all range
			for(int i=0; i<cnt_radi.length-1; i++){cnt_radi[i+1]+=cnt_radi[i];}

			for(int i=value.length-1; i>-1; i--){
				int remainder = base + (value[i]/denom)%base;
				buf[cnt_radi[remainder]-1]=value[i];
				cnt_radi[remainder]--;
			}
			value = buf;
		}
		return (value);
	}
}
