CREATE TABLE pocdb.message_details (
                       id bigint NOT NULL AUTO_INCREMENT,
                       channel_name varchar(100) NOT NULL,
                       sent_date_time TIMESTAMP NOT NULL,
                       PRIMARY KEY (id)
)DEFAULT CHARSET=UTF8MB4;

CREATE TABLE pocdb.transaction_details (
                        id bigint NOT NULL AUTO_INCREMENT,
                        rx_ac_id varchar(50) NOT NULL,
                        sender_ac_id varchar(50) NOT NULL,
                        amount decimal(15,2) NOT NULL,
                        service_code varchar(50) NOT NULL,
                        pe_code varchar(50) NOT NULL,
                        tx_id varchar(50) NOT NULL,
                        mop_id varchar(50) NOT NULL,
                        eod_id varchar(50) NOT NULL,
                        po_id varchar(50) NOT NULL,
                        message_detail_id bigint NOT NULL,
                        INDEX message_ind (message_detail_id),
                        FOREIGN KEY (message_detail_id)
                        REFERENCES message_details(id),
                        PRIMARY KEY (id)
)DEFAULT CHARSET=UTF8MB4;
