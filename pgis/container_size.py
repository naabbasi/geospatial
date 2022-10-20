import os
import shutil

import pandas as pd


def calculate_container_square_meters(df_container_size: pd.DataFrame):
    containers_info = []
    for index, value in enumerate(df_container_size.to_numpy()):
        length_string = (str(value[1]).split(' ')[0]).removesuffix("m")
        length_as_number = float(length_string)
        width_string = (str(value[3]).split(' ')[0]).removesuffix("m")
        width_as_number = float(width_string)
        square_meters = length_as_number * width_as_number
        container_info = {
            'container_name': value[0], 'length': length_as_number, 'width': width_as_number,
            'square_meter': '%.4f' % square_meters
        }
        containers_info.append(container_info)

    return containers_info


def save_calculated_container_square_meters(containers_info: list):
    calculated_data_frame = pd.DataFrame(containers_info)
    calculated_data_frame.to_csv('output.csv', sep=',', encoding='utf-8')


# if functools.reduce(lambda x, y: x and y, map(lambda p, q: p == q, containers_info, containers_info1), True):
#     print("The lists l1 and l2 are the same")
# else:
#     print("The lists l1 and l2 are not the same")

def main():
    os.chdir('../data')
    shutil.copyfile('Containers-Size.csv', 'Containers-Size-temp.csv')

    container_size_data_frame = pd.read_csv('Containers-Size-temp.csv')
    pd.set_option('display.max_rows', None)

    # container_size_data_frame = container_size_data_frame.drop(labels=['Unnamed: 0'], axis=1)
    containers_info = calculate_container_square_meters(container_size_data_frame)
    print(containers_info)
    save_calculated_container_square_meters(containers_info)


if __name__ == '__main__':
    main()
    os.remove('Containers-Size-temp.csv')
    quit()
