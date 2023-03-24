insert into wallet_test_db.wallet (id, address, balance)
values  (0x4DA46A6A2D7048239116C50F6A0CFA4E, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e942', 98.019821799999990000000000000000),
        (0x7265879FF8DD4E9BA365B07FC3D8C8CE, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e981', 100.000000000000000000000000000000),
        (0x8D106102ED3543278374BDFDBC719D54, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e970', 101.980178200000010000000000000000);

insert into wallet_test_db.transaction (id, amount, receiver_address, sender_address)
values  (0x3F54A56FC8A146C9ADA3F56E749A1F55, 0.990089099999999900000000000000, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e970', '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e981'),
        (0x8FCD989AD705476DB57131CB17DE04E5, 0.990089099999999900000000000000, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e981', '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e942'),
        (0x94CBEAD9C205419FB40514289D32DCCA, 0.990089099999999900000000000000, '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e970', '0x9e73e12B0A4c4ba4f4B346A7c23D657d79C7e942');