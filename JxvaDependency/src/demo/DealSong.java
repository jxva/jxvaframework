package demo;

import java.util.List;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.util.AlphaUtil;

public class DealSong {

	private static final DAOFactory factory=DAOFactory.getInstance();
	public static void main(String[] args) {
		DAO dao=factory.createDAO();
		List<Song> list=(List<Song>)dao.find("from Song where spellName is null");
		for(Song song:list){
			song.setSpellName(AlphaUtil.toAlpha(song.getName()));
			dao.update(song);
			//System.out.println(song.getName()+"="+song.getSpellName());
		}
		dao.close();

	}

}
