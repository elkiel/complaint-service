CREATE TABLE IF NOT EXISTS complaints (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    content TEXT NOT NULL,
    reporter VARCHAR(255) NOT NULL,
    country VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    counter NUMERIC(10, 2) NOT NULL DEFAULT 1,
    CONSTRAINT unique_product_reporter UNIQUE (product_id, reporter)
);
