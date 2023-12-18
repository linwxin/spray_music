package com.xin.spray;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.xin.spray.databinding.ActivityMainBinding;
import com.xin.spray.ui.musicplayer.MusicPlayerFragment;
import com.xin.spray.ui.my.MyFragment;
import com.xin.spray.ui.playlist.PlaylistFragment;
import com.xin.spray.ui.scanner.ScannerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ViewPager2 viewPager2;

    private FragmentStateAdapter pagerAdapter;

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navView = findViewById(R.id.nav_view);

        // ViewPage
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PlaylistFragment.newInstance());
        fragmentList.add(MusicPlayerFragment.newInstance());
        fragmentList.add(ScannerFragment.newInstance());
        fragmentList.add(MyFragment.newInstance());

        viewPager2 = findViewById(R.id.nav_host_fragment_activity_main);
        pagerAdapter = new ScreenSlidePagerAdapter(this, fragmentList);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setOffscreenPageLimit(4);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                onPagerSelected(position);
            }
        });

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_playlist)
                    viewPager2.setCurrentItem(0);
                if (item.getItemId() == R.id.navigation_music_player)
                    viewPager2.setCurrentItem(1);
                if (item.getItemId() == R.id.navigation_scanner)
                    viewPager2.setCurrentItem(2);
                if (item.getItemId() == R.id.navigation_my)
                    viewPager2.setCurrentItem(3);

                return true;
            }
        });

    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList;
        public ScreenSlidePagerAdapter(FragmentActivity fa, List<Fragment> fragmentList) {
            super(fa);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    private void onPagerSelected(int position) {
        switch (position) {
            case 0:
                navView.setSelectedItemId(R.id.navigation_playlist);
                break;
            case 1:
                navView.setSelectedItemId(R.id.navigation_music_player);
                break;
            case 2:
                navView.setSelectedItemId(R.id.navigation_scanner);
                break;
            case 3:
                navView.setSelectedItemId(R.id.navigation_my);
                break;
        }
    }


}