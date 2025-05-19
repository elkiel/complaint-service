INSERT INTO complaints (id, product_id, content, reporter, country, created_at, updated_at, counter)
VALUES
  ('11111111-1111-1111-1111-111111111111', '00000000-0000-0000-0000-000000000001', 'Product stopped working', 'test1@example.com', 'PL', now(), now(), 1.00),
  ('22222222-2222-2222-2222-222222222222', '00000000-0000-0000-0000-000000000002', 'Received wrong item', 'test2@example.com', 'DE', now(), now(), 2.00);
