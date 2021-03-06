package cravebot.results.elysi.results;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

import cravebot.R;
import cravebot.customfont.TextViewPlus;

/**
 * Created by elysi on 12/24/2015.
 */

public class SectionsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static ArrayList<FoodItem> items;
    private static String APIFood, APIResto;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<FoodItem> items, String APIFood, String APIResto) {
        super(fm);
        this.items = items;
        this.APIFood = APIFood;
        this.APIResto = APIResto;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println("PRINT: POS " + position);

        return new PlaceholderFragment().newInstance(position);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    public static class PlaceholderFragment extends Fragment {

        private final static String ARG_SECTION_NUMBER = "section_number";

        private int width, height;
        private ImageView foodImage, background, backgroundInfo;
        private static FoodItem singleItem;
        private View.OnClickListener lis;
        View view;
        private String fontHatten ="fonts/HATTEN.TTF";
        private String fontGadugi = "fonts/gadugi.ttf";



        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            System.out.println("PRINT: INSTANCE" + sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }


        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_card_layout, container, false);
            singleItem = items.get(getArguments().getInt(ARG_SECTION_NUMBER));



            System.out.println("PRINT: SHOW" + singleItem.getItemName());
            TextViewPlus foodName = (TextViewPlus) view.findViewById(R.id.foodName);
            TextViewPlus restoName = (TextViewPlus) view.findViewById(R.id.restoName);
            TextViewPlus price = (TextViewPlus) view.findViewById(R.id.price);

//            Typeface hat = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/HATTEN.TTF");
//            Typeface gad = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "fonts/gadugi.ttf");

//            foodName.setTypeface(hat);
//            restoName.setTypeface(gad);
//            price.setTypeface(hat);




            foodName.setText(singleItem.getItemName());
            restoName.setText(singleItem.getRestoName());
            price.setText("P " + Double.toString(singleItem.getPrice()));

//            foodName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            restoName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            foodImage = (ImageView) view.findViewById(R.id.foodImage);
            background = (ImageView) view.findViewById(R.id.background);
            backgroundInfo = (ImageView) view.findViewById(R.id.backgroundInfo);
            UrlImageViewHelper helper = new UrlImageViewHelper();
            UrlImageViewHelper.setUrlDrawable(foodImage, APIFood + singleItem.getPhoto());
            foodImage.invalidate();
            background.invalidate();
            backgroundInfo.invalidate();

            Button testMore = (Button) view.findViewById(R.id.testMore);
////            testMore.setOnClickListener(this.lis);
            testMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FrameLayout moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
                    moreInfo.setVisibility(View.VISIBLE);

                    for (int i = 0; i < moreInfo.getChildCount(); i++) {
                        View child = moreInfo.getChildAt(i);
                        child.setVisibility(View.VISIBLE);
                        child.postInvalidate();
                    }

                    FrameLayout place = (FrameLayout) view.findViewById(R.id.root_frame);
                    place.setVisibility(View.GONE);

                    for (int i = 0; i < place.getChildCount(); i++) {
                        View child = place.getChildAt(i);
                        child.setVisibility(View.GONE);
                        child.postInvalidate();
                    }

                    //   FragmentTransaction trans = getChildFragmentManager()
//                            .beginTransaction();
//
//
//				/*
//				 * IMPORTANT: We use the "root frame" defined in
//				 * "root_fragment.xml" as the reference to replace fragment
//				 */
//
//                    trans.replace(R.id.root_frame, new MoreInfoFragment().newInstance(getArguments().
//                            getInt(ARG_SECTION_NUMBER)));
//
//				/*
//				 * IMPORTANT: The following lines allow us to add the fragment
//				 * to the stack and return to it later, by pressing back
//				 */
//                    trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    trans.addToBackStack(null);
//
//                    trans.commit();
                }
            });

            TextViewPlus foodNameInfo = (TextViewPlus) view.findViewById(R.id.foodNameInfo);
            TextViewPlus restoNameInfo = (TextViewPlus) view.findViewById(R.id.restoNameInfo);
            TextViewPlus priceInfo = (TextViewPlus) view.findViewById(R.id.priceInfo);


//            foodNameInfo.setTypeface(hat);
//            restoNameInfo.setTypeface(gad);
//            priceInfo.setTypeface(hat);



            foodNameInfo.setText(singleItem.getItemName().trim());
            restoNameInfo.setText(singleItem.getRestoName().trim());
            priceInfo.setText("P " + Double.toString(singleItem.getPrice()).trim());

            TextViewPlus description = (TextViewPlus) view.findViewById(R.id.description);
            description.setText(singleItem.getDescription().trim());

            //restoLogo = (ImageView) view.findViewById(R.id.restoLogo);

//            UrlImageViewHelper helper = new UrlImageViewHelper();
//            helper.setUrlDrawable(restoLogo, APIResto+singleItem.getRestoLogo());


            TextViewPlus option1 = (TextViewPlus) view.findViewById(R.id.option1);
            TextViewPlus option2 = (TextViewPlus) view.findViewById(R.id.option2);
            TextViewPlus option3 = (TextViewPlus) view.findViewById(R.id.option3);
            TextViewPlus option4 = (TextViewPlus) view.findViewById(R.id.option4);
            TextViewPlus option5 = (TextViewPlus) view.findViewById(R.id.option5);
            TextViewPlus option6 = (TextViewPlus) view.findViewById(R.id.option6);

            TextViewPlus price1 = (TextViewPlus) view.findViewById(R.id.price1);
            TextViewPlus price2 = (TextViewPlus) view.findViewById(R.id.price2);
            TextViewPlus price3 = (TextViewPlus) view.findViewById(R.id.price3);
            TextViewPlus price4 = (TextViewPlus) view.findViewById(R.id.price4);
            TextViewPlus price5 = (TextViewPlus) view.findViewById(R.id.price5);
            TextViewPlus price6 = (TextViewPlus) view.findViewById(R.id.price6);


            option1.setText(singleItem.getOption1().trim());
            option2.setText(singleItem.getOption2().trim());
            option3.setText(singleItem.getOption3().trim());
            option4.setText(singleItem.getOption4().trim());
            option5.setText(singleItem.getOption5().trim());
            option6.setText(singleItem.getOption6().trim());

            price1.setText(singleItem.getPrice1().trim());
            price2.setText(singleItem.getPrice2().trim());
            price3.setText(singleItem.getPrice3().trim());
            price4.setText(singleItem.getPrice4().trim());
            price5.setText(singleItem.getPrice5().trim());
            price6.setText(singleItem.getPrice6().trim());


            ImageButton back = (ImageButton) view.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FrameLayout place = (FrameLayout) view.findViewById(R.id.root_frame);
                    place.setVisibility(View.VISIBLE);

                    for (int i = 0; i < place.getChildCount(); i++) {
                        View child = place.getChildAt(i);
                        child.setVisibility(View.VISIBLE);
                        child.postInvalidate();
                    }

                    FrameLayout moreInfo = (FrameLayout) view.findViewById(R.id.root_frameInfo);
                    moreInfo.setVisibility(View.GONE);

                    for (int i = 0; i < moreInfo.getChildCount(); i++) {
                        View child = moreInfo.getChildAt(i);
                        child.setVisibility(View.GONE);
                        child.postInvalidate();
                    }


                }
            });

            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
            FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            return view;
        }

        public void onDestroy(){
            super.onDestroy();
            unbindDrawables(view.findViewById(R.id.fragmentLayoutWhole));
            System.gc();

        }


        private void unbindDrawables(View view) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        }

        public void onStop()
        {
            super.onStop();
            foodImage.setImageBitmap(null);
            backgroundInfo.setImageBitmap(null);
            background.setImageBitmap(null);
        }

    }




}


