/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;

/**
 *
 * @author Diego Murillo
 */
public class FeedMessage extends AMessage{
    private PostMessage post;
    private boolean reacted = false;
    private boolean liked = false;
    private boolean interchanged = false;
    
    public FeedMessage(PostMessage post){
        super(post.getTopic());
        this.post = post;
        
    }

    public PostMessage getPost() {
        return post;
    }

    public void setPost(PostMessage post) {
        this.post = post;
    }

    public boolean isInterchanged() {
        return interchanged;
    }

    public void setInterchanged(boolean interchanged) {
        this.interchanged = interchanged;
    }

    
    public boolean isReacted() {
        return reacted;
    }

    public void setReacted(boolean reacted) {
        this.reacted = reacted;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    
    
}
