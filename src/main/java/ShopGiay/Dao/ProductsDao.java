package ShopGiay.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ShopGiay.Dto.ProductsDto;
import ShopGiay.Dto.ProductsDtoMapper;

@Repository
public class ProductsDao extends BaseDao{
	
	private final boolean YES = true;
	private final boolean NO = false;
	
	private StringBuffer SqlString() {
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("p.id as id_product ");
		sql.append(", p.id_category ");
		sql.append(", p.size ");
		sql.append(", p.name ");
		sql.append(", p.price ");
		sql.append(", p.sale ");
		sql.append(", p.title ");
		sql.append(", p.bestseller ");
		sql.append(", p.new_product ");
		sql.append(", p.details ");
		sql.append(", c.id as id_color ");
		sql.append(", c.name as name_color ");
		sql.append(", c.code as code_color ");
		sql.append(", c.img ");
		sql.append(", p.created_at ");
		sql.append(", p.updated_at ");
		sql.append("FROM ");
		sql.append("products AS p ");
		sql.append("INNER JOIN ");
		sql.append("color AS c ");
		sql.append("ON p.id = c.id_product ");		
		return sql;
	}
	private String SqlProducts(boolean bestSeller, boolean newProduct ) {
		StringBuffer  sql =  SqlString();		
		sql.append("WHERE 1 = 1 ");
		if(bestSeller) {
			sql.append(" AND p.bestseller = true ");
			
		}
		if(newProduct) {
			sql.append(" AND p.new_product = true ");
		}		
		sql.append("GROUP BY p.id, c.id_product ");
		sql.append("ORDER BY RAND() ");
		if(bestSeller) {
			sql.append("LIMIT 9 ");
		}
		if(newProduct) {
			sql.append("LIMIT 5 ");		
		}
		return sql.toString();
	}
	private String SqlProductsByID(int id ) {
		StringBuffer  sql =  SqlString();		
		sql.append("WHERE 1 = 1 ");
		sql.append(" AND id_category = " + id + " ");
		
		return sql.toString();
	}
	private String SqlProductsPaginate(int start, int end ) {
		StringBuffer  sql =  SqlString();		
		sql.append(" LIMIT " + start + ", "+ end); 
		return sql.toString();
	}
	public List<ProductsDto> GetDataProducts() {
		String sql = SqlProducts(NO,YES);
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}
	public List<ProductsDto> GetAllProductsByID(int id) {
		String sql = SqlProductsByID(id);
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}
	public List<ProductsDto> GetDataProductsPaginates(int start, int end) {
		String sql = SqlProductsPaginate(start,end);
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}
}
