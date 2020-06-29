package cn.edu.bit.cangjingge.user.service;

import cn.edu.bit.cangjingge.common.entity.Bookshelf;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.user.dao.BookshelfDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookshelfServiceImpl {
    @Resource
    BookshelfDao bookshelfDao;

//    public Response<Bookshelf> getBookshelfById(Long id){
//        Bookshelf bookshelf = bookshelfDao.getBookshelfById(id);
//        if ((bookshelf == null))
//            throw new BusinessException(ResponseStatusEnum.BOOKSHELF_NOT_FOUND);
//        return ResponseUtil.success(bookshelf);
//    }

    public Response<List<Bookshelf>> getBookshelfByUserId(Long userId){
        List<Bookshelf> bookshelves = bookshelfDao.getBookshelfByUserId(userId);
        return ResponseUtil.success(bookshelves);
    }

    public Response<Bookshelf> createBookshelf(Long userId,Long fictionId){
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setFictionId(fictionId);
        bookshelf.setUserId(userId);
        if(bookshelfDao.createBookshelf(bookshelf) != -1)
            return ResponseUtil.success(bookshelf);
        throw new BusinessException(ResponseStatusEnum.BOOKSHELF_CREATION_FAILURE);
    }

    public Response<String> deleteBookshelf(Long userId,Long fictionId){
        Bookshelf bookshelf = bookshelfDao.getBookshelfByOtherId(userId,fictionId);
        if (bookshelf != null){
            bookshelfDao.deleteBookshelfById(bookshelf.getId());
            return ResponseUtil.success();
        }
        throw new BusinessException(ResponseStatusEnum.BOOKSHELF_NOT_FOUND);
    }

}
