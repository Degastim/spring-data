INSERT INTO user_accounts (user_account_id,money) VALUES (1,100);
INSERT INTO users (user_id,name,email,user_account_id) VALUES (1,'Jon','jon@gmail.com',1);
INSERT INTO events (event_id,title,date) VALUES (1,'Dance',to_timestamp('05 Dec 2000', 'DD Mon YYYY'));
INSERT INTO tickets(ticket_id,user_id,event_id,place,ticket_price,category) VALUES (1,1,1,2,3,'BAR');
