Path.Direction.CCW����counter-clockwise��д��ָ������ʱ�뷽��ľ���·����
Path.Direction.CW����clockwise����д��ָ����˳ʱ�뷽��ľ���·����

1. �����Σ����ߣ�
addArc(RectF oval, float startAngle, float sweepAngle)
�ڶ�������Ϊ0ʱ��λ���Ǿ����ұ�1/2�߶ȵĵ㣬90Ϊ���εײ�1/2���λ�ã�������....����Ϊ˳ʱ����ת����������ʱ����ת��
������������ͼ�λ��ƽǶȣ���ͼ����������Ϊ180�������-180����ôͼ�ε�������

2. ��Բ
addCircle(float x, float y, float radius, Path.Direction dir)
��һ����������Բ�����꣬���������ǰ뾶�����Ĳ�����˳ʱ�뻭������ʱ�뻭��ɶ���⣿����

3. ����Բ
addOval(RectF oval, Path.Direction dir)

4. addPath(Path src, float dx, float dy)


����һ��Path�����������Ƶ�src��һ�У�����X��Y�᷽���ƶ��ڶ����������ľ��롣

5. addRect(RectF rect, Path.Direction dir)

6. ��������
addRect(float left, float top, float right, float bottom, Path.Direction dir)
�ĸ�������Ӧ��ԭ�����Ծ�����Ǹ��㡣

7. ��Բ�Ǿ���
addRoundRect(RectF rect, float rx, float ry, Path.Direction dir)
�ڶ�����������Ϊ0ʱ���Ǹ����Σ�Ϊ360ʱ�����Ǹ���Բ��
�ڶ�������ָX�᷽��ĽǶȣ���������ο����εĺ��߽���λ�ã�0-360�������㷶ΧΪ �ǵ������е�֮���ĳ�㡣

8. arcTo(RectF oval, float startAngle, float sweepAngle)
��ͬ��arcTo(RectF oval, float startAngle, float sweepAngle, boolean false)�����Է��֣���֮ǰ�����һ�㿪ʼ���ߵ�����Բ�Ŀ�ʼ�㣬���Ż�����Բ��

9. arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
������һ������Ϊtrue����ô��ͬ��addArc(RectF oval, float startAngle, float sweepAngle)��

10. cubicTo(float x1, float y1, float x2, float y2, float x3, float y3)
�����������ߡ�ǰ�ĸ��������������Ƶ㣬���������������ֹ�㡣
��ʼ��ͨ��moveTo(float x, float y)����setLastPoint(float dx, float dy)�������á�
���ڱ��������ߣ�����ȥ�����������ϡ�ĳ�˵Ĳ��ͣ����ڴ����ߡ�

11.moveTo(float x, float y)
������һ��ͼ�εĿ�ʼ�㡣

12.setLastPoint(float dx, float dy)
����ͼ�ε����һ����λ�á���������Ǹ����ͼ�Σ�������㲻��ͼ�����ϣ�
��ô����������һ��ͼ����������ɷ�ա���ͼ���������˸�Բ�Ǿ��Σ����setLastPoint��һ�¡�

13.close()
�رյ�ǰͼ�Σ�������һ�㲻�ǿ�ʼ���ǵ㣬��ô�����һ�㻭�ߵ���ʼ�㡣�����֮����������ֻ��Ҫ�������ߣ��ٵ��˷����������;�����ˡ�

14.lineTo(float x,float y)
 ��һ�������һ�㵽��ǰ����ߡ�

15.rLineTo(float dx,float dy)
 ��һ�����ߣ������һ��Ϊ���յ����Ϊƫ������

