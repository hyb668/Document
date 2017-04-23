package github.common.helper;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
public class ListViewHelper
{
	private ListView listView;
	private boolean isBottom = false;
	private OnLoadMoreListener loadMoreListener;
	public ListViewHelper(ListView listView) {
		this.listView  = listView;
		this.listView.setOnScrollListener(new MyOnScrollListener());
	}
	private final class MyOnScrollListener implements OnScrollListener
	{
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
			if(scrollState==SCROLL_STATE_IDLE && isBottom){
				if(loadMoreListener!=null){
					loadMoreListener.onLoadMore();
				}
			}
			isBottom=false;
		}
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){	
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				isBottom = true;
			}
		}
	}
	public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
		this.loadMoreListener = loadMoreListener;
	}
	public interface OnLoadMoreListener{
		public void onLoadMore();
	}
}
