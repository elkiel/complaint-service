INSERT INTO complaints (
    id, product_id, content, reporter, country, created_at, updated_at, counter
) VALUES
    ('00000000-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 'Content A', 'user1@example.com', 'PL', now(), now(), 1),
    ('00000000-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222', 'Content B', 'user2@example.com', 'DE', now(), now(), 2),
    ('00000000-0000-0000-0000-000000000003', '33333333-3333-3333-3333-333333333333', 'Content C', 'user3@example.com', 'FR', now(), now(), 1);
