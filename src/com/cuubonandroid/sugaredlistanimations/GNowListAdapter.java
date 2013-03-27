package com.cuubonandroid.sugaredlistanimations;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * 
 * @author <a href="http://www.hugofernandes.pt">Hugo Fernandes</a>
 * 
 */
public abstract class GNowListAdapter extends GenericBaseAdapter {

  public GNowListAdapter(Context context, SpeedScrollListener scrollListener, List<? extends Object> items) {
    super(context, scrollListener, items);
  }

  @Override
  protected void defineInterpolator() {
    interpolator = new DecelerateInterpolator();
  }

  @Override
  protected View getAnimatedView(int position, View convertView, ViewGroup parent) {
    v = getRowView(position, convertView, parent);

    if (v != null && !positionsMapper.get(position) && position > previousPostition) {

      speed = scrollListener.getSpeed();

      animDuration = (((int) speed) == 0) ? ANIM_DEFAULT_SPEED : (long) (1 / speed * 15000);

      if (animDuration > ANIM_DEFAULT_SPEED)
        animDuration = ANIM_DEFAULT_SPEED;

      previousPostition = position;

      v.setTranslationY(height);
      v.setPivotX(width / 2);
      v.setPivotY(height / 2);
      v.setAlpha(0.0F);

      if (position % 2 == 0) {
        v.setTranslationX(-(width / 1.2F));
        v.setRotation(50);
      } else {
        v.setTranslationX((width / 1.2F));
        v.setRotation(-50);
      }

      ViewPropertyAnimator localViewPropertyAnimator =
          v.animate().rotation(0.0F).translationX(0).translationY(0).setDuration(animDuration).alpha(1.0F)
              .setInterpolator(interpolator);

      localViewPropertyAnimator.setStartDelay(500).start();

      positionsMapper.put(position, true);
    }

    return v;
  }

  /**
   * Get a View that displays the data at the specified position in the data
   * set. You can either create a View manually or inflate it from an XML layout
   * file. When the View is inflated, the parent View (GridView, ListView...)
   * will apply default layout parameters unless you use
   * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
   * to specify a root view and to prevent attachment to the root.
   * 
   * @param position The position of the item within the adapter's data set of
   *          the item whose view we want.
   * @param convertView The old view to reuse, if possible. Note: You should
   *          check that this view is non-null and of an appropriate type before
   *          using. If it is not possible to convert this view to display the
   *          correct data, this method can create a new view.
   * @param parent The parent that this view will eventually be attached to
   * @return A View corresponding to the data at the specified position.
   */
  protected abstract View getRowView(int position, View convertView, ViewGroup parent);
}
