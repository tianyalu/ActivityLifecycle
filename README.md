[TOC]
## 一、概述
### 1.1 Activity的生命周期
Activity的生命周期中有7个方法，Fragment生命周期中有11个方法  
![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/activity_lifecycle.png)

### 1.2 Activity和Fragment生命周期对比
Activity生命周期中除了以下6个方法外还有一个onRestart()方法，该方法在该Activity从不可见（仍存在）到重新可见时调用
![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/activity_fragment_lifecycle.jpeg)

## 二、测试代码及日志输出
### 2.1 测试代码
```android
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Activity--onCreate()");
        setContentView(R.layout.activity_main);


        android.app.Fragment tFragment = new TFragment();
        getFragmentManager().beginTransaction().add(R.id.FLayout,tFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Activity--onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Activity--onResumed()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"Activity--onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"Activity--onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"Activity--onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Activity--onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"Activity--onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"Activity--onRestoreInstanceState()");
    }

    public static class TFragment extends android.app.Fragment{

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Log.e(TAG,"Fragment--onAttach()");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.e(TAG,"Fragment--onCreate()");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.e(TAG,"Fragment--onCreateView()");
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.e(TAG,"Fragment--onActivityCreated()");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.e(TAG,"Fragment--onStart()");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.e(TAG,"Fragment--onResume()");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.e(TAG,"Fragment--onPause()");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.e(TAG,"Fragment--onStop()");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.e(TAG,"Fragment--onDestroyView()");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e(TAG,"Fragment--onDestroy()");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.e(TAG,"Fragment--onDetach()");
        }
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.sty.activity.lifecycle.MainActivity">

    <FrameLayout
        android:id="@+id/FLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </FrameLayout>
</LinearLayout>
```
### 2.2 测试Log
1. 运行程序：  

![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/on_create.png)  

2. 切换到其他应用：  

![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/on_stop.png)  

3. 切换回本应用：  

![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/on_restart.png)  

4. 退出应用：  

![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/on_destroy.png)  

5. 横竖屏切换：  

![image](https://github.com/tianyalu/ActivityLifecycle/blob/master/show/save_restore_instance_state.png)  

## 三、总结
**特别注意**：`由于Fragment依赖Activity的存在而存在，故而在创建Activity生命周期中的方法均先于Fragment生命周期中的方法；相反，在销毁时，先执行Fragment生命周期中的方法，再执行Activity生命周期中的方法。`
值得一提的是，Fragment生命周期中的方法onAttact(Context context)在执行该程序中并没有执行，查阅资料发现在API低于23版本时不会执行该方法，而是执行onAttach(Activity activity)方法。参考：[差点被坑死，Fragment onAttach方法没有被调用](http://blog.csdn.net/cadi2011/article/details/53314055)   

## 四、补充：Activity在异常情况下的生命周期
### 4.1 资源相关的系统配置发生改变导致activity被杀死并重新创建（横竖屏切换）
#### 4.1.1 Activity执行过程
* Activity
当系统配置发生变化时，activity会被杀死，即调用onPause、onStop、onDestroy方法，同时会调用onSavedInstanceState方法来保存activity的状态，这个方法调用是在onStop之前，与onPause没有既定的时序关系。这个方法只有在activity被异常终止的时候才会调用，正常情况是不会调用的。当activity重新创建时，系统会调用onRestoreInstanceState，并把activity销毁时onSavedInstanceState保存的Bundle对象传递给onRestoreInstanceState和onCreate方法。因此我们可以通过这两个方法来判断activity是否被重建，从时序来说，onCreate应该在onRestoreInstanceState之前执行。
#### 4.1.2 View执行过程
这里说一下这个onSavedInstanceState和onRestoreInstanceState方法。这玩意不仅存在activity中，也存在于各个view中。Activity中的这两个方法恢复的是activity的视图结构，而对应的数据恢复在对应的view中，比如TextView
关于保存和恢复view层次结构，系统的工作流程是这样的：  

① 首先activity被意外终止时，activity会调用onSavedInstanceState方法去保存数据  

② Activity会委托window去保存数据，接着window再委托他上面的顶级容器去保存数据。（顶层容器是一个viewgroup，一般来说她很可能是decorview）  

③ 最后由顶层容器再去一一通知他的子元素来保存数据。  

### 4.2 资源内存不足导致低优先级的activity被杀死
#### 4.2.1 优先级
① 前台activity：与用户交互的activity，优先级最高  

② 可见但非前台的activity：比如activity出现了一个弹窗，导致activity可见但位于后台不能进行交互  

③ 后台activity：已经被暂停的activity，比如执行了onStop方法，优先级最低  

参考：[Activity生命周期（异常情况）](https://blog.csdn.net/weixin_38703938/article/details/81233928)
  
  
本文转自：[测试Activity和Fragment的生命周期](https://www.cnblogs.com/LangZXG/p/6501839.html)
