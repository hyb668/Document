view�Ĵ�С���и�view���Լ��Ĵ�С�����ģ������ǵ�һ�����ġ�
measure�Ĺ��̱����Ͼ��ǰ�Match_parent��wrap_contentת��Ϊʵ�ʴ�С ������Ĳ�����������ͨ��measure����onMeasure()��ʵ�ֵģ�
measure��final ���ͣ����ܱ���д��

public final void measure(int widthMeasureSpec, int heightMeasureSpec)
����������������и�view���ݹ����ģ�Ҳ���Ǵ����˸�view�Ĺ�񡣶���ϵͳWindow���DecorVIew����Modeһ�㶼ΪMeasureSpec.EXACTLY ��
��size�ֱ��Ӧ��Ļ��ߡ�������View��˵��С���ɸ�View����View��ͬ�����ġ�
������������ɣ���һ���֣���16λ��ʾMODE��������MeasureSpec���У����������ͣ�
MeasureSpec.EXACTLY����ʾȷ����С��match_parent|100dip 
MeasureSpec.AT_MOST����ʾ����С�� 
MeasureSpec.UNSPECIFIED����ȷ���� wrap_content
 �ڶ����֣���16λ��ʾsize������view�Ĵ�С�������Ϊʲô����������дonmeasure��������Ҫ��
 int specMode = MeasureSpec.getMode(spec); 
 int specSize = MeasureSpec.getSize(spec);
 �������ã���ΪMeasureSpec֪����ô��ȡ�����ڸ�view��������������xml�������ĵ�һ��Ԫ�أ���
 ����ϵͳ��Window�����decorVIew����Modeһ�㶼ΪMeasureSpec.EXACTLY ��
 ��size�ֱ��Ӧ��Ļ���ߡ�Ҳ����Window��һ�ε��õ�view�����view����Xml�������ĵ�һ��Ԫ�أ���һ�㶼�����ֵ��
 ��������view��˵��������ֵ��������xml���������  android:layout_width��android:layout_height�����
 ��Ȼ������˵��view�Ĵ�С���и�view����view��ͬ�����ģ����������е㲻�ԣ�������Դ��������ֵ��
 ��˼�����ˣ����ǿ���measure�����ʲô�� 