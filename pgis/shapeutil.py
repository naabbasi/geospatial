import os

import geopandas as gpd
import matplotlib.pyplot as plt
import pandas as pd


def data_frame():
    df = pd.DataFrame({
        'City': ['Buenos Aires', 'Brasilia', 'Santiago', 'Bogota', 'Caracas'],
        'Country': ['Argentina', 'Brazil', 'Chile', 'Colombia', 'Venezuela'],
        'Latitude': [-34.58, -15.78, -33.45, 4.60, 10.48],
        'Longitude': [-58.66, -47.91, -70.66, -74.08, -66.86]
    })
    return df


def geo_data_frame():
    df = data_frame()
    gdf = gpd.GeoDataFrame(df, geometry=gpd.points_from_xy(df.Longitude, df.Latitude))
    print(gdf.head())
    print(gdf.area)
    return gdf


def world_map():
    world = gpd.read_file(gpd.datasets.get_path('naturalearth_lowres'))
    # We restrict to South America.
    ax = world[world.continent == 'South America'].plot(color='orange', edgecolor='black')

    # We can now plot our ``GeoDataFrame``.
    gdf = geo_data_frame()
    gdf.plot(ax=ax, color='red')
    plt.show()


def main():
    os.chdir("../data")

    print("======================= Geo JSON =======================")
    geo_data_frames = gpd.read_file("geojson/ct1.geojson")
    print('Type of dataframe: ', type(geo_data_frames))
    print(geo_data_frames.head())
    geo_data_frames = geo_data_frames.to_crs(epsg=6933)
    print('Geo Json Area: ', geo_data_frames.area)

    print("======================= Shape File =======================")
    data_frames = gpd.read_file("Data GDAL tutorial-20221003/roads.shp")
    print('Type of dataframe: ', type(data_frames))
    print(data_frames.head())

    data_frames = data_frames.set_geometry('geometry')
    data_frames = data_frames.to_crs(crs=3857)
    print('Area: ', data_frames.area)

    if __name__ == "__main__":
        main()

        world = gpd.read_file(gpd.datasets.get_path('naturalearth_lowres'))
        cnames = ['Austria', 'Sweden', 'Kenya']
        epsgs = ['3857', '3395', '6933']

        for c in cnames:
            carea = world[world['name'] == c]
            for e in epsgs:
                carea = carea.to_crs(epsg=e)
                area = int(pd.to_numeric(carea['geometry'].area) / 10 ** 6)
                print(c, ' : ', area)
