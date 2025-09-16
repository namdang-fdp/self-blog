'use client';

import { useEffect, useState } from 'react';
import { fullBlogPosts, comments } from '../../../../constants/sample-data';
import { Skeleton } from '@/components/ui/skeleton';
import { Button } from '@/components/ui/button';
import { useParams, useRouter } from 'next/navigation';
import { ArrowLeft, Bookmark, Eye, Flag, Heart, Share2 } from 'lucide-react';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { toast } from 'sonner';
import Image from 'next/image';
import { Separator } from '@/components/ui/separator';

export default function BlogPost() {
    const router = useRouter();
    const params = useParams();
    const [post, setPost] = useState<any>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [isLiked, setIsLiked] = useState(false);
    const [likes, setLikes] = useState(0);
    const [showReportDialog, setShowReportDialog] = useState(false);

    useEffect(() => {
        const postId = Number.parseInt(params.id as string);
        const foundPost = fullBlogPosts.find((p) => p.id === postId);
        if (foundPost) {
            setPost(foundPost);
            setLikes(foundPost.likes);
        }
        setTimeout(() => setIsLoading(false), 800);
    }, [params.id]);

    const handleLike = () => {
        if (isLiked) {
            setLikes((prev) => prev - 1);
            toast.success('Removed from favorites');
        } else {
            setLikes((prev) => prev + 1);
            toast.success('Added to favorites');
        }
        setIsLiked(!isLiked);
    };

    const handleShare = async () => {
        if (navigator.share) {
            try {
                await navigator.share({
                    title: post.title,
                    text: post.excerpt,
                    url: window.location.href,
                });
                toast.success('Shared successfully');
            } catch (error) {
                // User cancelled sharing
            }
        } else {
            await navigator.clipboard.writeText(window.location.href);
            toast.success('Link copied to clipboard');
        }
    };

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
                        <div className="flex items-start justify-center">
                            <Avatar className="h-12 w-12">
                                <AvatarFallback>
                                    {post.author
                                        .split(' ')
                                        .map((n: string) => n[0])
                                        .join('')}
                                </AvatarFallback>
                            </Avatar>

                            <div className="flex items-center gap-4 ml-4 mt-2">
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
                        <div className="flex items-center space-x-2">
                            <Button
                                variant="ghost"
                                size="sm"
                                onClick={handleLike}
                                className={`flex items-center space-x-1 ${
                                    isLiked
                                        ? 'text-red-500 hover:text-red-600'
                                        : 'text-muted-foreground hover:text-red-500'
                                }`}
                            >
                                <Heart
                                    className={`h-4 w-4 ${isLiked ? 'fill-current' : ''}`}
                                />
                                <span>{likes}</span>
                            </Button>

                            <Button
                                variant="ghost"
                                size="sm"
                                onClick={handleShare}
                            >
                                <Share2 className="h-4 w-4" />
                            </Button>

                            <Button
                                variant="ghost"
                                size="sm"
                                onClick={() => setShowReportDialog(true)}
                                className="text-muted-foreground hover:text-destructive"
                            >
                                <Flag className="h-4 w-4" />
                            </Button>
                        </div>
                    </div>
                </div>
                <div className="mb-8 rounded-lg overflow-hidden">
                    <Image
                        src="/assets/blog-login.jpg"
                        alt={post.title}
                        width={800}
                        height={400}
                        className="w-full h-64 md:h-96 object-cover"
                    />
                </div>
                <div
                    className="text-foreground leading-relaxed space-y-6"
                    dangerouslySetInnerHTML={{
                        __html: post.content,
                    }}
                />
                <Separator className="my-8" />
            </article>
        </div>
    );
}
