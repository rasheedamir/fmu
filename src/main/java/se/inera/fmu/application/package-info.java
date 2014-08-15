/**
 * Created by Rasheed on 7/5/14.
 *
 * Application Services
 *
 * The application services are responsible for driving workflow and coordinating transaction management (by use of the
 * declarative transaction management support in Spring). They also provide a high-level abstraction for clients to use
 * when interacting with the domain. These services are typically designed to define or support specific use cases.
 *
 * In some situations, e.g. when dealing with graphs of lazy-loaded domain objects or when passing services' return
 * values over network boundaries, the services are wrapped in facades. The facades handle ORM session management issues
 * and/or convert the domain objects to more portable Data Transfer Objects) that can be tailored to specific use cases.
 * In that case, we consider the DTO-serializing facade part of the interfaces layer.
 *
 * <p> This is the application layer: code that's needed for the application to
 * performs its tasks. It defines, or is defined by, use cases. </p>
 * <p> It is thin in terms of knowledge of domain business logic,
 * although it may be large in terms of lines of code.
 * It coordinates the domain layer objects to perform the actual tasks.  </p>
 * <p>
 *
 * </p> This layer is suitable for spanning transactions, security checks and high-level logging</p>
 *
 */
package se.inera.fmu.application;
