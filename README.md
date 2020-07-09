# CustomChart

## Gradle：

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


 Add the dependency

	dependencies {
	        implementation 'com.github.Henry7604:CustomChart:1.0'
	}
  
  ## Usage：
  
    <HorizontalScrollView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">
	
        <com.henry.chart.ChartView
            android:id="@+id/chart"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:gradient_color_end="@color/end_y"
            app:gradient_color_start="@color/start_y"
            app:paint_line_color="@color/dark_lavender" />

    </HorizontalScrollView>
