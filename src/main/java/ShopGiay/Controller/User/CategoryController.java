package ShopGiay.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ShopGiay.Dto.PaginatesDto;
import ShopGiay.Service.User.CategoryServiceImpl;
import ShopGiay.Service.User.PaginateServiceImpl;

@Controller
public class CategoryController extends BaseController{
	@Autowired
	private CategoryServiceImpl categoryService;
	@Autowired
	private PaginateServiceImpl paginateService;
	
	int totalProductsPage = 9;
	
	@RequestMapping(value= "/san-pham/{id}")
	public ModelAndView Product(@PathVariable String id) {
		_mvShare.addObject("categorys", _homeService.GetDataCategorys());
		_mvShare.addObject("products", _homeService.GetDataProducts());
		_mvShare.addObject("slides", _homeService.GetDataSlide());
		
		_mvShare.setViewName("user/products/category");
		int totalData = categoryService.GetAllProductsByID(Integer.parseInt(id)).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginates(totalData, totalProductsPage, 1);
		_mvShare.addObject("Idcategory",id);
		_mvShare.addObject("paginateInfo",paginateInfo);
		_mvShare.addObject("productsPagine", categoryService.GetDataProductsPaginate(paginateInfo.getStart(), paginateInfo.getEnd()));
		return _mvShare ;
	}
	
	
	@RequestMapping(value= "/san-pham/{id}/{currentPage}")
	public ModelAndView Product(@PathVariable String id,@PathVariable String currentPage) {
		_mvShare.addObject("categorys", _homeService.GetDataCategorys());
		_mvShare.addObject("products", _homeService.GetDataProducts());
		_mvShare.addObject("slides", _homeService.GetDataSlide());

		
		_mvShare.setViewName("user/products/category");
		int totalData = categoryService.GetAllProductsByID(Integer.parseInt(id)).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginates(totalData, totalProductsPage, Integer.parseInt(currentPage));
		_mvShare.addObject("IdCategory",id);
		_mvShare.addObject("paginateInfo",paginateInfo);
		_mvShare.addObject("productsPagine", categoryService.GetDataProductsPaginate(paginateInfo.getStart(), paginateInfo.getEnd()));
		return _mvShare ;
	}

}
