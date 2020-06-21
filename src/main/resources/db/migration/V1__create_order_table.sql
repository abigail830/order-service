create TABLE ORDER_TBL (
  ID VARCHAR(32) NOT NULL,
  STATUS varchar(20) NOT NULL,
  TOTAL_PRICE DECIMAL NOT NULL,
  CREATED_AT TIMESTAMP NOT NULL,
  PROVINCE VARCHAR(50) NOT NULL,
  CITY VARCHAR(50) NOT NULL,
  DETAIL VARCHAR(300) NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create TABLE ORDER_ITEM_TBL (
  ORDER_ID VARCHAR(32) NOT NULL,
  PRODUCT_ID VARCHAR(32) NOT NULL,
  COUNT DECIMAL NOT NULL,
  PRICE DECIMAL NOT NULL,
  PRIMARY KEY (ORDER_ID, PRODUCT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
