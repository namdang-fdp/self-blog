import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
    output: 'standalone',
    images: {
        domains: ['dorriss-blog-bucket.s3.ap-southeast-2.amazonaws.com'],
    },
};

export default nextConfig;
