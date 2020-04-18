package us.redshift.auth.security;

public class ExceptionHandlerFilter {
   /* private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        int flag=1;
        try {
            filterChain.doFilter(request, response);
        }
        catch (ServletException e) {
            log.error(e.getMessage());
            ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED,e.getMessage(),e);

            response.getWriter().write(convertObjectToJson(apiError));
            flag=0;
            // throw new ServletException(e.getMessage());

        }
        if(flag==0){
            filterChain.doFilter(request, response);
        }

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }*/
}