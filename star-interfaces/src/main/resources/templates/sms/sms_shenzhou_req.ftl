<Group Login_Name="${account}" Login_Pwd="${password}" OpKind="51" InterFaceID="" SerType=”${types}”>
    <E_Time>${times}</E_Time>
    <Mobile>
        <#list mobiles as item >
            ${item}<#if item_has_next>,</#if>
        </#list>
    </Mobile>
    <Content><![CDATA[${content}]]></Content>
    <ClientID>${snCode}</ClientID>
</Group>