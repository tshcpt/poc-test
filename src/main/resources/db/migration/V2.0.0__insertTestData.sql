insert into message_details(id, channel_name, sent_date_time)
values (1, "API_GATEWAY", CURRENT_TIMESTAMP);
insert into transaction_details(rx_ac_id, sender_ac_id, amount, service_code, pe_code, tx_id, mop_id, eod_id,
                                po_id, message_detail_id)
values ("TSIP", "TVAT", 1.8, "IN1001", "10004", "1234", "MC", "EURO2020278", "abc", 1);
insert into transaction_details(rx_ac_id, sender_ac_id, amount, service_code, pe_code, tx_id, mop_id, eod_id,
                                po_id, message_detail_id)
values ("TDMNET", "TREVIN", 1.8, "BK10", "10004", "1234", "MC", "EURO2020278", "abc", 1);
insert into transaction_details(rx_ac_id, sender_ac_id, amount, service_code, pe_code, tx_id, mop_id, eod_id,
                                po_id, message_detail_id)
values ("TREVIN", "TSIP", 1.8, "BK10", "10004", "1234", "MC", "EURO2020278", "abc", 1);
insert into transaction_details(rx_ac_id, sender_ac_id, amount, service_code, pe_code, tx_id, mop_id, eod_id,
                                po_id, message_detail_id)
values ("TSIP", "TDMNET", 1.8, "LC13", "10004", "1234", "MC", "EURO2020278", "abc", 1);
