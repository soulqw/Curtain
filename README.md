# Curtain
 [![Hex.pm](https://img.shields.io/badge/download-0.0.5-green)](https://bintray.com/beta/#/soulqw/AndroidFrame/curtain?tab=overview)
 [![Hex.pm](https://img.shields.io/badge/Jetpack-AndroidX-orange)]()
 [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)
#### 一个更简洁好用的高亮蒙层库：
 - 一行代码完成某个View,或者多个View的高亮展示
 - 支持基于AapterView(如ListView、GridView) 和RecyclerView 的item以及item中元素的高亮
 - 自动识别圆角背景,也可以自定义高亮形状
 - 高亮区域支持自定义大小、操作灵活
 - 顺应变化,基于Android X
 - 配置简单，导入方便
 
 ![image](https://img-blog.csdnimg.cn/20191009181206920.png)

## Installation：

```java
dependencies {
    implementation 'com.qw:curtain:0.0.5'
}

```
## Usage：
- 仅仅是高亮某个View
```java
    private void showCurtain(){
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.textView))
                .show();
    }
```
![image](https://upload-images.jianshu.io/upload_images/4346197-92c1944753653bbe.png)

- 如果你希望那个view的蒙层区域更大一些:

```java
   private void showCurtain(){
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.textView))
                .withPadding(findViewById(R.id.textView),24)
                .show();
    }

```
- 也可以同时高亮多个View:

```java
  private void showCurtain(){
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.textView))
                .with(findViewById(R.id.imageView))
                .show();
    }
```
![image](https://upload-images.jianshu.io/upload_images/4346197-a5b0376674a7b373.png)

- 如果你在蒙层上加上一些其他的元素,可以额外传入View布局:

```java
   private void showCurtain(){
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.textView))
                .setTopView(R.layout.nav_header_main)
                .show();
    }
```
![image](https://upload-images.jianshu.io/upload_images/4346197-f0f7453aae3cec80.png)

- 如果你想监听蒙层的展示或者消失的回调:

```java
   private void showCurtain(){
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.imageView))
                .setCallBack(new Curtain.CallBack() {
                    @Override
                    public void onShow(IGuide iGuide) {

                    }

                    @Override
                    public void onDismiss(IGuide iGuide) {

                    }
                }).show();
    }
```
- 默认会识别View的背景而生成相关高亮区域的形状,也可以自定形状:

```java
    private void showThirdGuide() {
        new Curtain(SimpleGuideActivity.this)
                .with(findViewById(R.id.btn_shape_custom))
                //圆角
                .withShape(findViewById(R.id.btn_shape_custom), new RoundShape(12))
                //椭圆形
//                .withShape(findViewById(R.id.btn_shape_custom),new CircleShape())
                // 也可继承自 Shape 自己实现形状
//                .withShape(findViewById(R.id.btn_shape_custom), new Shape() {
//                    @Override
//                    public void drawShape(Canvas canvas, Paint paint, HollowInfo info) {
                //draw your shape here
//                    }
//                })
                .show();
    }
```
- 在ListView 或者GridView 中使用:
```java
  /**
     * 高亮item
     */
    private void showGuideInItem() {
        View item1 = ViewGetter.getFromAdapterView(listView, 5);
        View item2 = ViewGetter.getFromAdapterView(listView, 2);
        //如果你的View的位置不在屏幕中，返回值为null 需要判空处理
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .with(item1)
                .with(item2)
                .show();
    }

    /**
     * 高亮item中的元素
     */
    private void showGuideInItemChild() {
        View item1 = ViewGetter.getFromAdapterView(listView, 1);
        View item2 = ViewGetter.getFromAdapterView(listView, 3);
        //如果你的View的位置不在屏幕中，返回值为null 需要判空处理
        if (null == item1 || null == item2) {
            return;
        }
        new Curtain(this)
                .withShape(item1.findViewById(R.id.image), new CircleShape())
                .with(item2.findViewById(R.id.tv_text))
                .show();
    }
```
![image](https://upload-images.jianshu.io/upload_images/11595074-cef0c4894f88a5d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 其他一些功能介绍:

```java
    private void showCurtain() {
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.imageView))
                //是否允许回退关闭蒙层
                .setCancelBackPressed(false)
                // 设置蒙层背景颜色
                .setCurtainColor(0x88000000)
                // 设置蒙层出现的动画 默认渐隐
                .setAnimationStyle(R.style.testAnimation)
                .show();
    }
```
#### demo 截图:

![image](https://upload-images.jianshu.io/upload_images/11595074-7c7a71b1de643c18.gif?imageMogr2/auto-orient/strip)

![image](https://upload-images.jianshu.io/upload_images/11595074-3c8fc50488da539b.gif?imageMogr2/auto-orient/strip)

[Github地址](https://github.com/soulqw/Curtain)