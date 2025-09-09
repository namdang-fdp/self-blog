'use client';
import { BlogCard } from '@/components/blog-card';
import { FeaturedCarousel } from '@/components/feature-carousel';
import { blogPosts } from '../../constants/sample-data';

export default function BlogListPage() {
    return (
        <div className="min-h-screen bg-background">
            <section className="pt-25 pb-16 px-4 sm:px-6 lg:px-8">
                <div className="max-w-7xl mx-auto">
                    <div className="text-center mb-12">
                        <h1 className="text-4xl md:text-6xl font-bold text-foreground mb-6 text-balance">
                            Discover Amazing Stories
                        </h1>
                        <p className="text-xl text-muted-foreground max-w-2xl mx-auto text-pretty leading-relaxed">
                            Explore our curated collection of articles,
                            tutorials, and insights from industry experts and
                            passionate writers.
                        </p>
                    </div>
                    <FeaturedCarousel />
                </div>
            </section>

            <section className="py-16 px-4 sm:px-6 lg:px-8 bg-muted/30">
                <div className="max-w-7xl mx-auto">
                    <div className="flex items-center justify-between mb-12">
                        <h2 className="text-3xl font-bold text-foreground">
                            Latest Articles
                        </h2>
                        <div className="flex items-center space-x-4">
                            <select className="px-4 py-2 border border-border rounded-lg bg-background text-foreground focus:ring-2 focus:ring-primary focus:border-transparent">
                                <option>All Categories</option>
                                <option>Tutorial</option>
                                <option>Design</option>
                                <option>Performance</option>
                                <option>CSS</option>
                                <option>TypeScript</option>
                            </select>
                        </div>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        {blogPosts.map((post) => (
                            <BlogCard key={post.id} {...post} />
                        ))}
                    </div>

                    <div className="text-center mt-12">
                        <button className="px-8 py-3 bg-primary text-primary-foreground rounded-lg font-medium hover:bg-primary/90 transition-colors">
                            Load More Articles
                        </button>
                    </div>
                </div>
            </section>
        </div>
    );
}
