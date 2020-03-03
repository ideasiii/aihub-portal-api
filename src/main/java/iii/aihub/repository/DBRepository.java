package iii.aihub.repository;

import iii.aihub.entity.article.Article;

public interface DBRepository {

	public void save(Article article) throws DBException;
}
