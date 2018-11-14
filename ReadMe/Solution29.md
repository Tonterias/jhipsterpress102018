# Problem 29: Implement GetUserById


If you have an Entity structure like JHipsterPress you will find yourself with a lot of entities related to the User Entity and no method to get the user information if you have the userId (which is the one you are using in those related entities as a key to User). You have one to fetch the user if you have the login (getUserWithAuthoritiesByLogin).

Part of the things that you need to get this done are already there...(automatically generated) like


    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }
    
in the UserService or 

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesById(Long id);
    
within the UserRepository.

So let's create the UserResource method


    /**
     * GET /users/:id : get the "login" user.
     *
     * @param id the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/id/{id:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User by Id : {}", id);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthorities(id)
                .map(UserDTO::new));
    }

and it method to call it at the front-end (user.service.ts)

	 findById(id: number): Observable<HttpResponse<IUser>> {
        return this.http.get<IUser>(`${this.resourceUrl}/id/${id}`, { observe: 'response' });
    }
 
NOTE: I had some problems if I share the same url, so I added the /id/ to avoid them.