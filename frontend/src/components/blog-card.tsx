'use client';

import { useState } from 'react';
import { Heart, MessageCircle, Clock, User } from 'lucide-react';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';

interface BlogCardProps {
    id: number;
    title: string;
    excerpt: string;
    image: string;
    author: string;
    publishDate: string;
    readTime: string;
    likes: number;
    comments: number;
    category: string;
}

export function BlogCard({
    id,
    title,
    excerpt,
    image,
    author,
    publishDate,
    readTime,
    likes: initialLikes,
    comments,
    category,
}: BlogCardProps) {
    const [likes, setLikes] = useState(initialLikes);
    const [isLiked, setIsLiked] = useState(false);

    const handleLike = () => {
        if (isLiked) {
            setLikes((prev) => prev - 1);
        } else {
            setLikes((prev) => prev + 1);
        }
        setIsLiked(!isLiked);
    };

    return (
        <Card className="group overflow-hidden border-border hover:shadow-lg transition-all duration-300 hover:-translate-y-1 bg-card">
            <div className="relative overflow-hidden">
                <img
                    src={image || '/placeholder.svg'}
                    alt={title}
                    className="w-full h-48 object-cover transition-transform duration-300 group-hover:scale-105"
                />
                <div className="absolute top-3 left-3">
                    <span className="inline-block px-2 py-1 text-xs font-medium bg-primary text-primary-foreground rounded-md">
                        {category}
                    </span>
                </div>
            </div>

            <CardContent className="p-6">
                <div className="flex items-center text-sm text-muted-foreground mb-3 space-x-4">
                    <div className="flex items-center space-x-1">
                        <User className="h-4 w-4" />
                        <span>{author}</span>
                    </div>
                    <div className="flex items-center space-x-1">
                        <Clock className="h-4 w-4" />
                        <span>{readTime}</span>
                    </div>
                </div>

                <h3 className="text-xl font-bold text-card-foreground mb-3 text-balance leading-tight group-hover:text-primary transition-colors">
                    {title}
                </h3>

                <p className="text-muted-foreground mb-4 text-pretty leading-relaxed">
                    {excerpt}
                </p>

                <div className="flex items-center justify-between pt-4 border-t border-border">
                    <div className="flex items-center space-x-4">
                        <Button
                            variant="ghost"
                            size="sm"
                            className={`flex items-center space-x-1 transition-colors ${
                                isLiked
                                    ? 'text-red-500 hover:text-red-600'
                                    : 'text-muted-foreground hover:text-red-500'
                            }`}
                            onClick={handleLike}
                        >
                            <Heart
                                className={`h-4 w-4 ${isLiked ? 'fill-current' : ''}`}
                            />
                            <span className="text-sm font-medium">{likes}</span>
                        </Button>

                        <div className="flex items-center space-x-1 text-muted-foreground">
                            <MessageCircle className="h-4 w-4" />
                            <span className="text-sm font-medium">
                                {comments}
                            </span>
                        </div>
                    </div>

                    <span className="text-sm text-muted-foreground">
                        {publishDate}
                    </span>
                </div>
            </CardContent>
        </Card>
    );
}
