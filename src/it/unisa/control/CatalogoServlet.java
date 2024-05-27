package it.unisa.control;

import java.io.IOException; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProdottoDao prodDao = new ProdottoDao();
		ProdottoBean bean = new ProdottoBean();
		String sort = request.getParameter("sort");
		String action = request.getParameter("action");
		String redirectedPage = request.getParameter("page");;
	
		try {
			if(action!=null) {
				if(action.equalsIgnoreCase("add")) {
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
					bean.setInVendita(true);
					prodDao.doSave(bean);
				}
				
				else if(action.equalsIgnoreCase("modifica")) {
					
					bean.setIdProdotto(Integer.parseInt(request.getParameter("id")));
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
					bean.setInVendita(true);
					prodDao.doUpdate(bean);	
				}

				request.getSession().removeAttribute("categorie");

			}
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}


		try {
			request.getSession().removeAttribute("products");
			request.getSession().setAttribute("products", prodDao.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
			
			response.sendRedirect(request.getContextPath() + "/" +redirectedPage);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String iva = request.getParameter("iva");
            String prezzo = request.getParameter("prezzo");
            String dataUscita = request.getParameter("dataUscita");
            String quantita = request.getParameter("quantità");
            String img = request.getParameter("img");
            String piattaforma = request.getParameter("piattaforma");
            String genere = request.getParameter("genere");
            String descDett = request.getParameter("descDett");

            // Validazione dei parametri
            if (containsInvalidChars(nome) || containsInvalidChars(descrizione) || containsInvalidChars(iva) ||
                containsInvalidChars(prezzo) || containsInvalidChars(dataUscita) || containsInvalidChars(img) ||
                containsInvalidChars(piattaforma) || containsInvalidChars(genere) || containsInvalidChars(descDett)) {
                request.setAttribute("error", "Caratteri < e > non sono consentiti.");
                request.getRequestDispatcher("/admin/GestioneCatalogo.jsp").forward(request, response);
                return;
            }

            // Logica per aggiungere il prodotto
            // ...
        }
    }

    private boolean containsInvalidChars(String input) {
        return input != null && (input.contains("<") || input.contains(">") ||
                                 input.contains("&lt;") || input.contains("&gt;") ||
                                 input.contains("%3C") || input.contains("%3E"));
    }
}
