//package com.gateway.gatewaysecurity.security.filters;
//
//import com.learning.proj.food.domain.entity.AppUser;
//import com.learning.proj.food.domain.entity.Order;
//import com.learning.proj.food.domain.entity.UserRole;
//import com.learning.proj.food.exception.DeliveryApplicationException;
//import com.learning.proj.food.service.order.OrderService;
//import com.learning.proj.food.service.user.UserService;
//import com.learning.proj.food.utils.RegexUtils;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//@AllArgsConstructor
//public class OwnerFilter extends GenericFilterBean {
//
//    private final OrderService orderService;
//    private final UserService userService;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        String path = String.valueOf(((HttpServletRequest) request).getRequestURL());
//        if (path.contains("/orders") && !(path.contains("/preparation")
//                || path.contains("/delivery")
//                || path.contains("/pendingOrders")
//                || path.contains("/readyForDelivery")
//                || path.contains("/create"))
//        ) {
//            String orderId = RegexUtils.extractFirstMatch(path, "\\/orders\\/(.+)\\/?");
//            if (path.contains("/update/") && path.contains("/status")) {
//                orderId = RegexUtils.extractFirstMatch(path, "update\\/(.+)\\/status");
//            }
//
//            try {
//                Order order = orderService.findOrderById(orderId);
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                UserDetails principal = (UserDetails) authentication.getPrincipal();
//                if (principal.getAuthorities().stream().findFirst().isPresent()) {
//                    String role = principal.getAuthorities().stream().findFirst().get().toString().replace("ROLE_", "");
//                    if (!role.contains(UserRole.ADMIN.name())) {
//                        AppUser appUser = userService.findAppUserByEmailAndRole(principal.getUsername(), role);
//                        String appUserInfoId = appUser.getUserInfo().getId();
//                        if (!
//                                (order.getCreatedCustomerId().equals(appUserInfoId)
//                                        || order.getAssignedChefId().equals(appUserInfoId)
//                                        || order.getAssignedRiderId().equals(appUserInfoId))
//                        ) {
//                            return;
//                        }
//                    }
//                }
//            } catch (DeliveryApplicationException e) {
//                return;
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}