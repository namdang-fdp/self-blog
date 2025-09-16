'use client';

import { useEffect, useState } from 'react';
import { fullBlogPosts, comments } from '../../../../constants/sample-data';
import { Skeleton } from '@/components/ui/skeleton';
import { Button } from '@/components/ui/button';
import { useParams, useRouter } from 'next/navigation';
import { ArrowLeft, Eye } from 'lucide-react';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';

export default function BlogPost() {
    const router = useRouter();
    const params = useParams();
    const [post, setPost] = useState<any>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [likes, setLikes] = useState(0);

    useEffect(() => {
        const postId = Number.parseInt(params.id as string);
        const foundPost = fullBlogPosts.find((p) => p.id === postId);
        if (foundPost) {
            setPost(foundPost);
            setLikes(foundPost.likes);
        }
        setTimeout(() => setIsLoading(false), 800);
    }, [params.id]);

    if (isLoading) {
        return (
            <div className="min-h-screen bg-background">
                <header className="sticky top-0 z-50 bg-background/80 backdrop-blur-md border-b border-border">
                    <div className="container mx-auto px-4 py-4">
                        <Skeleton className="h-10 w-32" />
                    </div>
                </header>

                <article className="container mx-auto px-4 py-8 max-w-4xl">
                    <div className="mb-8">
                        <Skeleton className="h-6 w-20 mb-4" />
                        <Skeleton className="h-12 w-full mb-6" />
                        <div className="flex items-center space-x-4 mb-6">
                            <Skeleton className="h-12 w-12 rounded-full" />
                            <div className="space-y-2">
                                <Skeleton className="h-4 w-32" />
                                <Skeleton className="h-3 w-48" />
                            </div>
                        </div>
                    </div>
                    <Skeleton className="h-64 w-full mb-8 rounded-lg" />
                    <div className="space-y-4">
                        <Skeleton className="h-4 w-full" />
                        <Skeleton className="h-4 w-full" />
                        <Skeleton className="h-4 w-3/4" />
                    </div>
                </article>
            </div>
        );
    }

    if (!post) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <div className="text-center">
                    <h1 className="text-2xl font-bold text-foreground mb-2">
                        Post not found
                    </h1>
                    <p className="text-muted-foreground mb-4">
                        The blog post you are looking for does not exist.
                    </p>
                    <Button onClick={() => router.push('/')}>
                        Back to Home
                    </Button>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-background">
            <article className="container mx-auto px-4 py-24 max-w-4xl">
                <div className="mb-8">
                    <Badge variant="secondary" className="mb-4">
                        {post.category}
                    </Badge>

                    <h1 className="text-4xl md:text-5xl font-bold text-foreground mb-6 text-balance leading-tight">
                        {post.title}
                    </h1>

                    <div className="flex items-center justify-between flex-wrap gap-6 mb-6">
                        <div className="flex items-start justify-start">
                            <Avatar className="h-12 w-12">
                                <AvatarFallback>
                                    {post.author
                                        .split(' ')
                                        .map((n: string) => n[0])
                                        .join('')}
                                </AvatarFallback>
                            </Avatar>
                        </div>
                        <div className="flex items-center gap-6">
                            <p className="font-semibold text-foreground">
                                {post.author}
                            </p>
                            <div className="flex items-center space-x-4 text-sm text-muted-foreground">
                                <span>{post.publishDate}</span>
                            </div>
                            <div className="flex items-center space-x-1">
                                <Eye className="h-4 w-4" />
                                <span>{post.views} views</span>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
        </div>
    );
}
