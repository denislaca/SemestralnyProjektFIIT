package Models.Tracker;

/**
 * Created by Denis-iMac on 28.3.17.
 */
public interface Route {
    void setTimer(int initialTime);
    void setOnRoute(int time);
    void SortBeforeRoute(int left, int right);
}
