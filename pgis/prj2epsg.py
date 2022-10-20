import sys

from osgeo import osr


def esri_prj2standards(shape_prj_path):
    prj_file = open(shape_prj_path, 'r')
    prj_txt = prj_file.read()
    srs = osr.SpatialReference()
    srs.ImportFromESRI([prj_txt])

    print('Shape prj is: %s' % prj_txt)
    print('WKT is: %s' % srs.ExportToWkt())
    print('Proj4 is: %s' % srs.ExportToProj4())

    srs.AutoIdentifyEPSG()
    print('EPSG is: %s' % srs.GetAuthorityCode(None))


if __name__ == "__main__":
    esri_prj2standards(sys.argv[1])
