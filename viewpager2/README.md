# ViewPager2

相关资料：
[Viewpager2 demo](https://github.com/android/views-widgets-samples/tree/master/ViewPager2)  

demo主要介绍使用：  
Viewpager2的adapter view用RecyclerView.Adapter<CardViewHolder>；  
Fragment用FragmentStateAdapter  
notifyDataSetChanged的使用  
FragmentStateAdapter的notifyDataSetChanged使用  
TabLayout配合Viewpager2使用  
fakeDragBy使用  
PageTransformer使用  
离屏加载，预加载 offscreenPageLimit  
Viewpager2处理滑动冲突requestDisallowInterceptTouchEvent()  

[Look Deep Into ViewPager2 -全面的viewpager2入门](https://proandroiddev.com/look-deep-into-viewpager2-13eb8e06e419)  
[viewpager-》viewpager2](https://developer.android.com/training/animation/vp2-migration)  
(https://www.bilibili.com/video/av78983205/)  
[RecyclerView扩展(五) - ViewPager2的源码分析](https://juejin.im/post/5dbaf617e51d452a3679272e)  
[viewpager2源码](https://android.googlesource.com/platform/frameworks/support/+/androidx-master-dev/viewpager2/src/main/java/androidx/viewpager2/widget/ViewPager2.java)  
[ViewPager2的原理和使用](https://www.jianshu.com/p/86573026e314)  
[【ViewPager2避坑系列】瞬间暴增数个Fragment](https://hitendev.github.io/2019/05/19/%E3%80%90ViewPager2%E9%81%BF%E5%9D%91%E7%B3%BB%E5%88%97%E3%80%91%E7%9E%AC%E9%97%B4%E6%9A%B4%E5%A2%9E%E6%95%B0%E4%B8%AAFragment/)  

官网上的：  
Cordinatorlayout appbarlayout的behavior viewpager2无法处理
viewpager2实现轮播图

The primary reason to migrate is that ViewPager2 is receiving active development support and ViewPager is not.  
官网说viewpager不再维护，让我们开始使用viewpager2，但是现阶段viewpager2还是有bug的
