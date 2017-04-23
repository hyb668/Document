view的大小是有父view和自己的大小决定的，而不是单一决定的。
measure的过程本质上就是把Match_parent和wrap_content转换为实际大小 。具体的测量工作，是通过measure调用onMeasure()来实现的，
measure是final 类型，不能被重写。

public final void measure(int widthMeasureSpec, int heightMeasureSpec)
这个两个参数都是有父view传递过来的，也就是代表了父view的规格。对于系统Window类的DecorVIew对象Mode一般都为MeasureSpec.EXACTLY ，
而size分别对应屏幕宽高。对于子View来说大小是由父View和子View共同决定的。
他有两部分组成，第一部分：高16位表示MODE，定义在MeasureSpec类中，有三种类型，
MeasureSpec.EXACTLY：表示确定大小，match_parent|100dip 
MeasureSpec.AT_MOST：表示最大大小， 
MeasureSpec.UNSPECIFIED：不确定。 wrap_content
 第二部分：低16位表示size，即父view的大小，这就是为什么，我们在重写onmeasure方法是需要：
 int specMode = MeasureSpec.getMode(spec); 
 int specSize = MeasureSpec.getSize(spec);
 这样调用，因为MeasureSpec知道怎么读取。对于跟view（并不是我们在xml中声明的第一个元素），
 而是系统的Window对象的decorVIew对象。Mode一般都为MeasureSpec.EXACTLY ，
 而size分别对应屏幕宽，高。也就是Window第一次掉用的view（这个view才是Xml中声明的第一个元素），一般都是这个值，
 而对于子view来说，这连个值就是你在xml定义的属性  android:layout_width和android:layout_height这个，
 当然了上面说过view的大小是有父view和子view共同决定的，所以这样有点不对，但是来源于这两个值。
 意思明白了，我们看看measure里边做什么了 