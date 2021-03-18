package com.example.home.features

import com.example.home.HomeFragment

interface HomeFeature {
    operator fun invoke(fragment: HomeFragment)
}