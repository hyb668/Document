android����view�Ĺ��̼����� :  ����View���Ļ�ͼ��������ViewRoot.java���performTraversals()����չ���ģ��ú�������ִ�й��̿ɼ򵥸ſ�Ϊ
����֮ǰ���õ�״̬������ִ���ж��Ƿ���Ҫ����������ͼ��С(measure)���Ƿ���Ҫ���¡����֡���ͼ��λ��(layout)���Լ��Ƿ���Ҫ���ػ桱 (draw)��
�Զ���ViewGroup��Ҫ��д����������
onMeasure������ ��View �Ŀ�ߣ������Լ��Ŀ�ߣ�
onLayout������ ��View ��λ��

1�� onMeasure������ ��View �Ĳ����ļ���Ϊ ��View ���ò���ģʽ������ֵ
������� = ����ģʽ + ����ֵ��
����ģʽ�� 3 �֣�
1) EXACTLY ��ȷֵ�� ��100dip��match_parent
2) AT_MOST ���ֵ��wrap_content
3) UNSPECIFIED ����ֵ���ӿؼ���Ҫ���Ͷ����ScrollView

ÿһ��ViewGroup��Ӧһ��LayoutParams
��View.getlayoutParams();  -> ���ؼ��� LayoutParams
Viewͨ��LayoutParams������丸��ͼ����Ҫ�Ĵ�С(�������ȺͿ��)��

FlowLayout��LayoutParamsֻ��Ҫʹ�� ��View ֮��ļ��ֵ������
FlowLayout��Ӧ��LayoutParams�� MarginLayoutParams

